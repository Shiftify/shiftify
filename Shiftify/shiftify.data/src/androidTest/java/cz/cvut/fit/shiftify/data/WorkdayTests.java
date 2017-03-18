package cz.cvut.fit.shiftify.data;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import org.greenrobot.greendao.database.Database;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.models.UserDao;

import static org.junit.Assert.assertEquals;

/**
 * Created by lukas on 16.11.2016.
 */

@RunWith(AndroidJUnit4.class)
public class WorkdayTests {
    @Before
    public void setUp() throws Exception {
        UserManager userManager = new UserManager();
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();

        userManager.deleteAll();
        scheduleTypeManager.deleteAll();

        scheduleTypes = new ArrayList<>();
        users = new ArrayList<>();
        schedules = new ArrayList<>();

        initScheduleTypes();
        initUsers();
        initSchedules();
        initExceptionInSchedules();
    }

    @Test
    public void testCalculateWorkDay() throws Exception {
        UserManager userManager = new UserManager();

        // Lukáš Pavel, 09. 02. 2017
        WorkDay workDay = userManager.shiftsForDate(users.get(0).getId(), new LocalDate(2017, 2, 9));
        Assert.assertEquals(1, workDay.shifts.size());
        Assert.assertEquals(new LocalTime(22, 0), workDay.shifts.get(0).getFrom());
        Assert.assertEquals(new Period(2, 0, 0, 0), workDay.shifts.get(0).getDuration());
        Assert.assertEquals("noční", workDay.shifts.get(0).getName());

        // Lukáš Pavel, 10. 02. 2017
        workDay = userManager.shiftsForDate(users.get(0).getId(), new LocalDate(2017, 2, 10));
        Assert.assertEquals(2, workDay.shifts.size());
        Assert.assertEquals(new LocalTime(0, 0), workDay.shifts.get(0).getFrom());
        Assert.assertEquals(new Period(4, 0, 0, 0), workDay.shifts.get(0).getDuration());
        Assert.assertEquals("noční", workDay.shifts.get(0).getName());
        Assert.assertEquals(new LocalTime(14, 0), workDay.shifts.get(1).getFrom());
        Assert.assertEquals(new Period(8, 0, 0, 0), workDay.shifts.get(1).getDuration());
        Assert.assertEquals("2. odpolední", workDay.shifts.get(1).getName());

        // Igor Ibramovich, 08. 02. 2017
        workDay = userManager.shiftsForDate(users.get(1).getId(), new LocalDate(2017, 2, 8));
        Assert.assertEquals(0, workDay.shifts.size());

        // Petr Ovčáček, 08. 02. 2017
        workDay = userManager.shiftsForDate(users.get(2).getId(), new LocalDate(2017, 2, 8));
        Assert.assertEquals(0, workDay.shifts.size());
    }

    @Test
    public void testCalculateListOfWorkDays() throws Exception {
        UserManager userManager = new UserManager();

        // Lukáš Pavel, 01. 03. 2017 - 15. 03. 2017
        List<WorkDay> workDays = userManager.shiftsForPeriod(users.get(0).getId(), new LocalDate(2017, 3, 1), new LocalDate(2017, 3, 15));
        Assert.assertEquals(15, workDays.size());
        Assert.assertEquals(1, workDays.get(0).getShifts().size());
        Assert.assertEquals(new Period(6, 0, 0, 0), workDays.get(0).getShifts().get(0).getDuration());
        Assert.assertEquals("2. noční", workDays.get(0).getShifts().get(0).getName());
        Assert.assertEquals(1, workDays.get(14).getShifts().size());
        Assert.assertEquals("2. ranní", workDays.get(14).getShifts().get(0).getName());

        // TODO: make more detailed test of this instance above
    }

    @Test
    public void testCalculateWorkDaysWithExceptions() throws Exception {
        UserManager userManager = new UserManager();

        List<WorkDay> workDays = userManager.shiftsForPeriod(users.get(1).getId(), new LocalDate (2017, 1, 15), new LocalDate(2017, 1, 16));
        Assert.assertEquals(2, workDays.size());
        Assert.assertEquals(1, workDays.get(0).getShifts().size());
        Assert.assertEquals(new LocalTime(21, 0), workDays.get(0).getShifts().get(0).getFrom());
        Assert.assertEquals(new LocalTime(22, 0), workDays.get(0).getShifts().get(0).getTo());
        Assert.assertEquals(3, workDays.get(1).getShifts().size());
        Assert.assertEquals(new LocalTime(0, 0), workDays.get(1).getShifts().get(0).getFrom());
        Assert.assertEquals(new LocalTime(0, 30), workDays.get(1).getShifts().get(0).getTo());
        Assert.assertEquals(new LocalTime(2, 30), workDays.get(1).getShifts().get(1).getFrom());
        Assert.assertEquals(new LocalTime(6, 0), workDays.get(1).getShifts().get(1).getTo());
        Assert.assertEquals(new LocalTime(22, 0), workDays.get(1).getShifts().get(2).getFrom());
        Assert.assertEquals(new LocalTime(0, 0), workDays.get(1).getShifts().get(2).getTo());

        WorkDay workDay = userManager.shiftsForDate(users.get(2).getId(), new LocalDate(2017, 3, 20));
        Assert.assertEquals(4, workDay.getShifts().size());
        Assert.assertEquals(new LocalTime(0, 0), workDay.getShifts().get(0).getFrom());
        Assert.assertEquals(new LocalTime(2, 45), workDay.getShifts().get(0).getTo());
        Assert.assertEquals(new LocalTime(2, 45), workDay.getShifts().get(1).getFrom());
        Assert.assertEquals(new LocalTime(2, 50), workDay.getShifts().get(1).getTo());
        Assert.assertEquals(new LocalTime(22, 0), workDay.getShifts().get(2).getFrom());
        Assert.assertEquals(new LocalTime(22, 10), workDay.getShifts().get(2).getTo());
        Assert.assertEquals(new LocalTime(23, 0), workDay.getShifts().get(3).getFrom());
        Assert.assertEquals(new LocalTime(0, 0), workDay.getShifts().get(3).getTo());

        workDay = userManager.shiftsForDate(users.get(1).getId(), new LocalDate(2017, 2, 17));
        Assert.assertEquals(10, workDay.getShifts().size());
        Assert.assertEquals(new LocalTime(0, 25), workDay.getShifts().get(0).getFrom());
        Assert.assertEquals(new LocalTime(1, 15), workDay.getShifts().get(0).getTo());
        Assert.assertEquals(new LocalTime(1, 35), workDay.getShifts().get(1).getFrom());
        Assert.assertEquals(new LocalTime(2, 0), workDay.getShifts().get(1).getTo());
        Assert.assertEquals(new LocalTime(3, 45), workDay.getShifts().get(2).getFrom());
        Assert.assertEquals(new LocalTime(5, 50), workDay.getShifts().get(2).getTo());
        Assert.assertEquals(new LocalTime(5, 55), workDay.getShifts().get(3).getFrom());
        Assert.assertEquals(new LocalTime(6, 0), workDay.getShifts().get(3).getTo());
        Assert.assertEquals(new LocalTime(6, 0), workDay.getShifts().get(4).getFrom());
        Assert.assertEquals(new LocalTime(8, 30), workDay.getShifts().get(4).getTo());
        Assert.assertEquals(new LocalTime(10, 50), workDay.getShifts().get(5).getFrom());
        Assert.assertEquals(new LocalTime(13, 30), workDay.getShifts().get(5).getTo());
        Assert.assertEquals(new LocalTime(22, 0), workDay.getShifts().get(6).getFrom());
        Assert.assertEquals(new LocalTime(22, 10), workDay.getShifts().get(6).getTo());
        Assert.assertEquals(new LocalTime(22, 15), workDay.getShifts().get(7).getFrom());
        Assert.assertEquals(new LocalTime(22, 30), workDay.getShifts().get(7).getTo());
        Assert.assertEquals(new LocalTime(22, 50), workDay.getShifts().get(8).getFrom());
        Assert.assertEquals(new LocalTime(22, 59), workDay.getShifts().get(8).getTo());
        Assert.assertEquals(new LocalTime(23, 1), workDay.getShifts().get(9).getFrom());
        Assert.assertEquals(new LocalTime(23, 35), workDay.getShifts().get(9).getTo());

        workDays = userManager.shiftsForPeriod(users.get(1).getId(), new LocalDate (2017, 2, 22), new LocalDate(2017, 2, 23));
        Assert.assertEquals(2, workDays.size());
        Assert.assertEquals(1, workDays.get(0).getShifts().size());
        Assert.assertEquals(new LocalTime(15, 0), workDays.get(0).getShifts().get(0).getFrom());
        Assert.assertEquals(new LocalTime(22, 0), workDays.get(0).getShifts().get(0).getTo());
        Assert.assertEquals(1, workDays.get(1).getShifts().size());
        Assert.assertEquals(new LocalTime(14, 0), workDays.get(1).getShifts().get(0).getFrom());
        Assert.assertEquals(new LocalTime(21, 0), workDays.get(1).getShifts().get(0).getTo());
    }

    private List<ScheduleType> scheduleTypes;
    private List<User> users;
    private List<Schedule> schedules;

    private void initScheduleTypes() throws Exception {
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        List<ScheduleShift> shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("ranní", new LocalTime(6, 0), new Period(8, 0, 0, 0), 2, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("1. odpolední", new LocalTime(14, 0), new Period(8, 0, 0, 0), 3));
        shifts.add(new ScheduleShift("noční", new LocalTime(22, 0), new Period(6, 0, 0, 0), 5));
        shifts.add(new ScheduleShift("2. odpolední", new LocalTime(14, 0), new Period(8, 0, 0, 0),  6));
        ScheduleType scheduleType = new ScheduleType("Test Železárny", 6, "Slouží jako rozpis pro vrátnýho.");
        scheduleType.setShifts(shifts);
        scheduleTypeManager.add(scheduleType);
        scheduleTypes.add(scheduleType);

        shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("1. ranní", new LocalTime(6, 0), new Period(8, 0, 0, 0), 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("2. ranní", new LocalTime(6, 0), new Period(8, 0, 0, 0), 2));
        shifts.add(new ScheduleShift("1. odpolední", new LocalTime(14, 0), new Period(8, 0, 0, 0), 3));
        shifts.add(new ScheduleShift("2. odpolední", new LocalTime(14, 0), new Period(8, 0, 0, 0), 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("1. noční", new LocalTime(22, 0), new Period(8, 0, 0, 0), 5));
        shifts.add(new ScheduleShift("2. noční", new LocalTime(22, 0), new Period(8, 0, 0, 0), 6, "komentář"));
        scheduleType = new ScheduleType("Test Železárny hasič", 8);
        scheduleType.setShifts(shifts);
        scheduleTypeManager.add(scheduleType);
        scheduleTypes.add(scheduleType);
    }

    private void initUsers() throws Exception {
        UserManager userManager = new UserManager();
        User user = new User("Lukáš", "Pavel");
        userManager.add(user);
        users.add(user);
        user = new User("Igor", "Ibramovich", "+421 777 111 333", "igor.ibra@gmail.com", "Igi");
        userManager.add(user);
        users.add(user);
        user = new User("Petr", "Ovčáček", null, "petr@ovcacek.io");
        userManager.add(user);
        users.add(user);
    }

    private void initSchedules() throws Exception {
        UserManager userManager = new UserManager();
        // Lukáš Pavel
        Schedule schedule = new Schedule(users.get(0).getId(), scheduleTypes.get(0).getId(), new LocalDate(2017, 1, 2), new LocalDate(2017, 2, 9), 3);
        userManager.addSchedule(schedule);
        schedules.add(schedule);
        schedule = new Schedule(users.get(0).getId(), scheduleTypes.get(1).getId(), new LocalDate(2017, 2, 10), new LocalDate(2017, 3, 9), 4);
        userManager.addSchedule(schedule);
        schedules.add(schedule);
        schedule = new Schedule(users.get(0).getId(), scheduleTypes.get(0).getId(), new LocalDate(2017, 3, 10), new LocalDate(2017, 3, 10), 5);
        userManager.addSchedule(schedule);
        schedules.add(schedule);
        schedule = new Schedule(users.get(0).getId(), scheduleTypes.get(1).getId(), new LocalDate(2017, 3, 11), new LocalDate(2017, 3, 31), 6);
        userManager.addSchedule(schedule);
        schedules.add(schedule);

        // Igor Ibramovich
        schedule = new Schedule(users.get(1).getId(), scheduleTypes.get(1).getId(), new LocalDate(2017, 1, 2), new LocalDate(2017, 2, 7), 8);
        userManager.addSchedule(schedule);
        schedules.add(schedule);
        schedule = new Schedule(users.get(1).getId(), scheduleTypes.get(1).getId(), new LocalDate(2017, 2, 13), new LocalDate(2017, 3, 9), 2);
        userManager.addSchedule(schedule);
        schedules.add(schedule);

        // Petr Ovčáček
        //   has no schedules whatsoever
    }

    private void initExceptionInSchedules() throws Exception {
        UserManager userManager = new UserManager();

        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(new LocalDate (2017, 1, 15), users.get(1).getId(), users.get(1).getSchedules().get(1).getId());
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(22, 0, 0), new Period(2, 0, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(21, 0, 0), new Period(1, 0, 0, 0), true));
        userManager.addExceptionInSchedule(exceptionInSchedule);

        exceptionInSchedule = new ExceptionInSchedule(new LocalDate(2017, 1, 16), users.get(1).getId(), users.get(1).getSchedules().get(1).getId());
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(0, 30, 0), new Period(2, 0, 0, 0), false));
        userManager.addExceptionInSchedule(exceptionInSchedule);

        exceptionInSchedule = new ExceptionInSchedule(new LocalDate(2017, 3, 20), users.get(2).getId());
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(0, 0), new Period(2, 45, 0, 0), true));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(2, 45), new Period(0, 5, 0, 0), true));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(22, 0), new Period(0, 10, 0, 0), true));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(23, 0), new Period(1, 0, 0, 0), true));
        userManager.addExceptionInSchedule(exceptionInSchedule);

        exceptionInSchedule = new ExceptionInSchedule(new LocalDate(2017, 2, 17), users.get(1).getId(), users.get(1).getSchedules().get(0).getId());
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(0, 0), new Period(0, 25, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(1, 15), new Period(0, 20, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(2, 0), new Period(1, 45, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(5, 50), new Period(0, 5, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(6, 0), new Period(2, 30, 0, 0), true));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(10, 50), new Period(2, 40, 0, 0), true));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(22, 10), new Period(0, 5, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(22, 30), new Period(0, 20, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(22, 59), new Period(0, 2, 0, 0), false));
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(23, 35), new Period(0, 25, 0, 0), false));
        userManager.addExceptionInSchedule(exceptionInSchedule);

        exceptionInSchedule = new ExceptionInSchedule(new LocalDate(2017, 2, 22), users.get(1).getId());
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(14, 0), new Period(1, 0, 0, 0), false));
        userManager.addExceptionInSchedule(exceptionInSchedule);

        exceptionInSchedule = new ExceptionInSchedule(new LocalDate(2017, 2, 23), users.get(1).getId());
        exceptionInSchedule.addExceptionShift(new ExceptionShift(new LocalTime(21, 0), new Period(1, 0, 0, 0), false));
        userManager.addExceptionInSchedule(exceptionInSchedule);
    }
}