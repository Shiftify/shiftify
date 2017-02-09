package cz.cvut.fit.shiftify.data;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;

/**
 * Created by lukas on 16.11.2016.
 */

public class WorkDayTests {
    @Test
    public void calculateWorkDaysTest() throws Exception {
        // Isn't working - need to test on device with working SQLite database
        List<ScheduleType> scheduleTypes = new ArrayList<>();
        List<ScheduleShift> shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("1. ranní", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 2, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("1. odpolední", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0), 3));
        shifts.add(new ScheduleShift("2. ranní", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 5, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("2. odpolední", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0),  6));
        ScheduleType scheduleType = new ScheduleType("Železárny", 6, "Slouží jako rozpis pro vrátnýho.");
        scheduleType.setShifts(shifts);
        scheduleTypes.add(scheduleType);

        shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("1. ranní", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("2. ranní", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(8, 0), 2));
        shifts.add(new ScheduleShift("1. odpolední", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0), 3));
        shifts.add(new ScheduleShift("2. odpolední", Utilities.GregCalFrom(14, 0), Utilities.GregCalFrom(8, 0), 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("1. noční", Utilities.GregCalFrom(22, 0), Utilities.GregCalFrom(8, 0), 5));
        shifts.add(new ScheduleShift("2. noční", Utilities.GregCalFrom(22, 0), Utilities.GregCalFrom(8, 0), 6, "komentář"));
        scheduleType = new ScheduleType("Železárny hasič", 8);
        scheduleType.setShifts(shifts);
        scheduleTypes.add(scheduleType);

        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule(1L, 2L, new GregorianCalendar(2017, 0, 2), new GregorianCalendar(2017, 1, 9), 3);
        schedule.setScheduleType(scheduleTypes.get(0));
        schedules.add(schedule);
        schedule = new Schedule(1L, 3L, new GregorianCalendar(2017, 1, 10), new GregorianCalendar(2017, 2, 9), 8);
        schedule.setScheduleType(scheduleTypes.get(0));
        schedules.add(schedule);

        //List<WorkDay> workDays = WorkDay.calculateWorkDays(new GregorianCalendar(2017, 1, 9), new GregorianCalendar(2017, 1, 11),
        //        schedules, new ArrayList<ExceptionInSchedule>());
    }
}