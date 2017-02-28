package cz.cvut.fit.shiftify.data;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        Schedule schedule = findSchedule(date);
        Schedule prevSchedule = findSchedule(date.minusDays(1));

        if (prevSchedule != null) {
            scheduleShifts.addAll(pickScheduleShifts(prevSchedule, date.minusDays(1), true));
        }

        if (schedule != null) {
            scheduleShifts.addAll(pickScheduleShifts(schedule, date, false));
        }

        ExceptionInSchedule exSchedule = findExceptionInSchedule(date);
        List<ExceptionShift> exceptionShifts = (exSchedule == null ? new ArrayList<ExceptionShift>() : exSchedule.getShifts());
        Collections.sort(scheduleShifts);
        Collections.sort(exceptionShifts);
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
        for (Schedule schedule : schedules) {
            if (!date.isBefore(schedule.getFrom()) && (schedule.getTo() == null || !date.isBefore(schedule.getTo()))) {
                return schedule;
            }
        }

        return null;
    }

    private List<ScheduleShift> pickScheduleShifts(Schedule schedule, LocalDate date, boolean isPrevious) {
        List<ScheduleShift> scheduleShifts = new ArrayList<>();
        int days = Days.daysBetween(schedule.getFrom(), date).getDays();
        int cycleLength = schedule.getScheduleType().getDaysOfScheduleCycle();
        int cycleDay = (schedule.getStartingDayOfScheduleCycle() + days - 1) % cycleLength;

        if (cycleDay == 0) {
            cycleDay = cycleLength;
        }

        for (ScheduleShift shift : schedule.getScheduleType().getShifts()) {
            if (cycleDay == shift.getDayOfScheduleCycle() && (!isPrevious || shift.persistsIntoNextDay())) {
                ScheduleShift shiftCp = new ScheduleShift(shift);

                if (isPrevious) {
                    LocalTime from = LocalTime.MIDNIGHT;
                    LocalTime duration = shift.getTo();

                    shiftCp.setFrom(from);
                    shiftCp.setDuration(duration);
                } else if (shift.persistsIntoNextDay()) {
                    LocalTime duration = new LocalTime(23, 59, 59).minus(new Period(shift.getFrom()));
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
            LocalTime fromTime = schShift.getFrom();

            for (ExceptionShift excShift : offWorkExceptions) {
                if (!excShift.getFrom().isAfter(schShift.getFrom()) && !excShift.getTo().isAfter(schShift.getTo())) {
                    LocalTime duration = excShift.getFrom().minus(new Period(schShift.getFrom()));

                    if (duration.isAfter(LocalTime.MIDNIGHT)) {
                        ScheduleShift newShift = new ScheduleShift(schShift);
                        newShift.setFrom(fromTime);
                        newShift.setDuration(duration);
                        shifts.add(newShift);
                    }

                    fromTime = excShift.getTo();
                }
            }

            if (fromTime.isBefore(schShift.getTo())) {
                LocalTime duration = schShift.getTo().minus(new Period(fromTime));

                ScheduleShift newShift = new ScheduleShift(schShift);
                newShift.setFrom(fromTime);
                newShift.setDuration(duration);
                shifts.add(newShift);
            }
        }

        shifts.addAll(workExceptions);
        Collections.sort(shifts);
        return shifts;
    }

}
