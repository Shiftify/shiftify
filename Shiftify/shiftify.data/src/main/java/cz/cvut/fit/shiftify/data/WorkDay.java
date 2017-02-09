package cz.cvut.fit.shiftify.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay implements Comparator<WorkDay>, Comparable<WorkDay> {
    public static WorkDay calculateWorkDays(GregorianCalendar date, List<Schedule> schedules, List<ExceptionInSchedule> exceptionInSchedules) {
        List<ScheduleShift> scheduleShifts = new ArrayList<>();
        Schedule schedule = findSchedule(schedules, date);
        Schedule prevSchedule = findSchedule(schedules, Utilities.GregCalPrevDay(date));
        if (prevSchedule != null)
            scheduleShifts.addAll(pickScheduleShiftsPreviousDay(prevSchedule, date));
        if (schedule != null)
            scheduleShifts.addAll(pickScheduleShifts(schedule, date));
        ExceptionInSchedule exSchedule = findExceptionInSchedule(exceptionInSchedules, date);
        List<ExceptionShift> exceptionShifts = (exSchedule == null ? new ArrayList<ExceptionShift>() : exSchedule.getShifts());
        Collections.sort(scheduleShifts);
        Collections.sort(exceptionShifts);
        List<Shift> shifts = overlayExceptionShiftsAndScheduleShifts(scheduleShifts, exceptionShifts);
        return new WorkDay(date, shifts);
    }
    public static List<WorkDay> calculateWorkDays(GregorianCalendar from, GregorianCalendar to, List<Schedule> schedules, List<ExceptionInSchedule> exceptionInSchedules) {
        List<WorkDay> workDays = new ArrayList<>();
        for (GregorianCalendar cal = Utilities.GregCalFromMillis(from.getTimeInMillis()); Utilities.GregCalSubtractDates(to, cal) >= 0; cal = Utilities.GregCalNextDay(cal))
            workDays.add(calculateWorkDays(cal, schedules, exceptionInSchedules));
        return new ArrayList<WorkDay>();
    }
    //Schedule
    private static Schedule findSchedule(List<Schedule> schedules, GregorianCalendar date) {
        for (Schedule schedule : schedules)
            if (Utilities.GregCalSubtractDates(schedule.getFrom(), date) <= 0
                    && (schedule.getTo() == null || Utilities.GregCalSubtractDates(schedule.getTo(), date) >= 0))
                return schedule;
        // found no schedule for this date
        return null;
    }
    private static List<ScheduleShift> pickScheduleShiftsPreviousDay(Schedule schedule, GregorianCalendar date) {
        List<ScheduleShift> scheduleShifts = new ArrayList<>();
        long days = Utilities.GregCalSubtractDates(date, schedule.getFrom());
        long daysInCycle = schedule.getScheduleType().getDaysOfScheduleCycle();
        long prevDayOfScheduleCycle = (schedule.getStartingDayOfScheduleCycle() + days - 1) % daysInCycle;
        prevDayOfScheduleCycle = (prevDayOfScheduleCycle == 0 ? daysInCycle : prevDayOfScheduleCycle);
        for (ScheduleShift shift : schedule.getScheduleType().getShifts())
            try {
                if (shift.getDayOfScheduleCycle() == prevDayOfScheduleCycle && shift.persistsIntoNextDay())
                    scheduleShifts.add(cutShiftBeginning(shift));
            } catch (Exception ex) { }
        return scheduleShifts;
    }
    private static List<ScheduleShift> pickScheduleShifts(Schedule schedule, GregorianCalendar date) {
        List<ScheduleShift> scheduleShifts = new ArrayList<>();
        long days = Utilities.GregCalSubtractDates(date, schedule.getFrom());
        long daysInCycle = schedule.getScheduleType().getDaysOfScheduleCycle();
        long dayOfScheduleCycle = (schedule.getStartingDayOfScheduleCycle() + days) % daysInCycle;
        dayOfScheduleCycle = (dayOfScheduleCycle == 0 ? daysInCycle : dayOfScheduleCycle);
        for (ScheduleShift shift : schedule.getScheduleType().getShifts())
            if (shift.getDayOfScheduleCycle() == dayOfScheduleCycle)
                scheduleShifts.add(cutShiftEnd(shift));
        return scheduleShifts;
    }
    //ExceptionInSchedule
    private static ExceptionInSchedule findExceptionInSchedule(List<ExceptionInSchedule> schedules, GregorianCalendar date) {
        for (ExceptionInSchedule exceptionInSchedule : schedules)
            if (Utilities.GregCalSubtractDates(exceptionInSchedule.getDate(), date) == 0)
                return exceptionInSchedule;
        // found no exceptionInSchedule for this date
        return null;
    }
    private static List<Shift> overlayExceptionShiftsAndScheduleShifts(List<ScheduleShift> scheduleShifts, List<ExceptionShift> exceptionShifts) {
        List<Shift> shifts = new ArrayList<>();
        List<ExceptionShift> workExceptions = new ArrayList<>();
        List<ExceptionShift> offWorkExceptions = new ArrayList<>();
        for (ExceptionShift shift : exceptionShifts)
            if (shift.getIsWorking())
                workExceptions.add(shift);
            else
                offWorkExceptions.add(shift);
        for (ScheduleShift schShift : scheduleShifts) {
            int current = schShift.getFromInSeconds();
            for (ExceptionShift excShift : offWorkExceptions) {
                if (schShift.getFromInSeconds() <= excShift.getFromInSeconds()
                        && schShift.getToInSeconds() >= excShift.getToInSeconds()) {
                    GregorianCalendar duration = Utilities.GregCalFromMillis((excShift.getFromInSeconds() - current)*1000);
                    if (duration.getTimeInMillis() > Utilities.GregCalFrom(0, 0).getTimeInMillis()) {
                        ScheduleShift newShift = new ScheduleShift(schShift);
                        newShift.setFrom(Utilities.GregCalFromMillis(current*1000));
                        newShift.setDuration(duration);
                        shifts.add(newShift);
                    }
                    current = excShift.getToInSeconds();
                }
            }
            if (current < schShift.getToInSeconds()) {
                GregorianCalendar duration = Utilities.GregCalFromMillis((schShift.getToInSeconds() - current)*1000);
                ScheduleShift newShift = new ScheduleShift(schShift);
                newShift.setFrom(Utilities.GregCalFromMillis(current*1000));
                newShift.setDuration(duration);
                shifts.add(newShift);
            }
        }
        shifts.addAll(workExceptions);
        Collections.sort(shifts);
        return shifts;
    }
    //ScheduleShifts
    private static ScheduleShift cutShiftBeginning(ScheduleShift shift) {
        if (shift.getToInSeconds() <= 24*60*60*1000)
            return shift;
        int to = shift.getToInSeconds()*1000;
        shift.setDuration(Utilities.GregCalFromMillis(to - 24*60*60*1000));
        shift.setFrom(Utilities.GregCalFrom(0, 0));
        return shift;
    }
    private static ScheduleShift cutShiftEnd(ScheduleShift shift) {
        if (shift.getToInSeconds() <= 24*60*60*1000)
            return shift;
        int duration = 24*60*60*1000 - shift.getFromInSeconds()*1000;
        shift.setDuration(Utilities.GregCalFromMillis(duration));
        return shift;
    }

    // Constructors
    public WorkDay() { this(null, null); }
    public WorkDay(GregorianCalendar date) { // no work this day
        this(date, null);
    }
    public WorkDay(GregorianCalendar date, List<Shift> shifts) {
        setDate(date);
        setShifts(shifts);
    }

    protected GregorianCalendar date;
    protected List<Shift> shifts;

    // Getters and setters
    public GregorianCalendar getDate() {
        return date;
    }
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
    public List<Shift> getShifts() {
        return shifts;
    }
    public void setShifts(List<Shift> shifts) {
        this.shifts = (shifts == null ? new ArrayList<Shift>() : shifts);
    }

    public boolean hasShifts() {
         return shifts.isEmpty();
    }

    // Comparator methods
    @Override
    public int compareTo(WorkDay workDay) {
        return compare(this, workDay);
    }
    @Override
    public int compare(WorkDay workDay1, WorkDay workDay2) {
        if (workDay1.shifts.size() == 0) {
            if (workDay2.shifts.size() == 0)
                return 0;
            return 1;
        }
        if (workDay2.shifts.size() == 0)
            return -1;
        long diff = workDay1.shifts.get(0).getFrom().getTimeInMillis() - workDay2.shifts.get(0).getFrom().getTimeInMillis();
        return (diff == 0 ? 0 : (diff > 0 ? 1 : -1));
    }
}
