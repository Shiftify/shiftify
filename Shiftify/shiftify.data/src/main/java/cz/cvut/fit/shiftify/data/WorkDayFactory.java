package cz.cvut.fit.shiftify.data;

import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * Created by ondra on 27.2.17.
 */

public class WorkDayFactory {
    private List<Schedule> schedules;
    private List<ExceptionInSchedule> exceptions;

    public WorkDayFactory(List<Schedule> schedules, List<ExceptionInSchedule> exceptions) {
        this.schedules = schedules;
        this.exceptions = exceptions;
    }

    /**
     * FIXME could be smarter (refactor)
     */
    public WorkDay createWorkDay(LocalDate date) {
        List<ScheduleShift> scheduleShifts = new ArrayList<>();
        Schedule prevSchedule = findSchedule(date.minusDays(1));
        Schedule schedule = findSchedule(date);

        if (prevSchedule != null) {
            scheduleShifts.addAll(pickScheduleShifts(prevSchedule, date.minusDays(1), true));
        }

        if (schedule != null) {
            scheduleShifts.addAll(pickScheduleShifts(schedule, date, false));
        }

        ExceptionInSchedule exSchedule = findExceptionInSchedule(date);
        List<ExceptionShift> exceptionShifts = (exSchedule == null ? new ArrayList<ExceptionShift>() : exSchedule.getShifts());
        List<Shift> shifts = overlayExceptionShiftsAndScheduleShifts(scheduleShifts, exceptionShifts);
        return new WorkDay(date, shifts);
    }

    public List<WorkDay> createWorkDayList(LocalDate from, LocalDate to) {
        LocalDate it = from;
        List<WorkDay> workDays = new ArrayList<>();

        while (!it.isAfter(to)) {
            workDays.add(createWorkDay(it));

            it = it.plusDays(1);
        }

        return workDays;
    }

    private Schedule findSchedule(LocalDate date) {
        if (schedules.size() == 0)
            return null;
        boolean isBefore = !date.isBefore(schedules.get(0).getFrom()),
                isAfter = (schedules.get(0).getTo() == null || !date.isAfter(schedules.get(0).getTo()));
        for (Schedule schedule : schedules) {
            if (!date.isBefore(schedule.getFrom()) && (schedule.getTo() == null || !date.isAfter(schedule.getTo()))) {
                return schedule;
            }
        }

        return null;
    }

    private List<ScheduleShift> pickScheduleShifts(Schedule schedule, LocalDate date, boolean isPreviousDate) {
        List<ScheduleShift> scheduleShifts = new ArrayList<>();
        int days = Days.daysBetween(schedule.getFrom(), date).getDays() + 1;
        int cycleLength = schedule.getScheduleType().getDaysOfScheduleCycle();
        int cycleDay = (schedule.getStartingDayOfScheduleCycle() + days - 1) % cycleLength;
        if (cycleDay == 0)
            cycleDay = cycleLength;

        for (ScheduleShift shift : schedule.getScheduleType().getShifts()) {
            if (cycleDay == shift.getDayOfScheduleCycle() && (!isPreviousDate || shift.persistsIntoNextDay())) {
                ScheduleShift shiftCp = new ScheduleShift(shift);

                if (isPreviousDate) {
                    LocalTime from = LocalTime.MIDNIGHT;
                    Period duration = shift.persistsIntoNextDay()
                            ? new Period(shift.getTo().getMillisOfDay())
                            : shift.getDuration();
                    shiftCp.setFrom(from);
                    shiftCp.setDuration(duration);
                } else if (shift.persistsIntoNextDay()) {
                    Period duration = shiftCp.getDuration()
                            .minus(new Period(shift.getTo().getMillisOfDay()));
                    shiftCp.setDuration(duration);
                }

                scheduleShifts.add(shiftCp);
            }
        }
        return scheduleShifts;
    }

    private ExceptionInSchedule findExceptionInSchedule(LocalDate date) {
        for (ExceptionInSchedule exceptionInSchedule : exceptions) {
            if (date.equals(exceptionInSchedule.getDate())) {
                return exceptionInSchedule;
            }
        }
        return null;
    }

    private static List<Shift> overlayExceptionShiftsAndScheduleShifts(List<ScheduleShift> scheduleShifts, List<ExceptionShift> exceptionShifts) {
        List<Shift> shifts = new ArrayList<>();
        List<ExceptionShift> workExceptions = new ArrayList<>();
        List<ExceptionShift> offWorkExceptions = new ArrayList<>();

        for (ExceptionShift shift : exceptionShifts) {
            if (shift.getIsWorking()) {
                workExceptions.add(shift);
            } else {
                offWorkExceptions.add(shift);
            }
        }

        for (ScheduleShift schShift : scheduleShifts) {
            LocalTime current = schShift.getFrom();
            for (ExceptionShift excShift : offWorkExceptions) {
                if ((schShift.getTo().getMillisOfDay() == new LocalTime(0, 0).getMillisOfDay() || !excShift.getFrom().isAfter(schShift.getTo()))
                        && (excShift.getTo().getMillisOfDay() == new LocalTime(0, 0).getMillisOfDay() || !schShift.getFrom().isAfter(excShift.getTo()))) {
                    Period duration = new Period(excShift.getFrom().getMillisOfDay()).minus(new Period(current.getMillisOfDay()));
                    if (duration.toStandardDuration().getMillis() > 0) {
                        ScheduleShift newShift = new ScheduleShift(schShift);
                        newShift.setFrom(current);
                        newShift.setDuration(duration);
                        shifts.add(newShift);
                    }
                    current = excShift.getTo();
                    if (current.getMillisOfDay() == new LocalTime(0, 0).getMillisOfDay())
                        break;
                }
            }
            Period duration = new Period(schShift.getTo().minus(new Period(current.getMillisOfDay())).getMillisOfDay());
            if (duration.toStandardDuration().getMillis() > 0) {
                ScheduleShift newShift = new ScheduleShift(schShift);
                newShift.setFrom(current);
                newShift.setDuration(duration);
                shifts.add(newShift);
            }
        }

        shifts.addAll(workExceptions);
        Collections.sort(shifts);
        return shifts;
    }
}
