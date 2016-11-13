package cz.cvut.fit.shiftify.data;

import java.util.Vector;
import java.util.Random;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class UserManager {
    public void add(User user) throws Exception {
        user.setId(5);
    }
    public void edit(int userId, User user) throws Exception {
        user.setId(userId);
    }
    public void delete(int userId) throws Exception {
    }
    public User user(int userId) throws Exception {
        User user = new User("Michal", "Plameňák", "777222111", "random@something.org");
        user.setId(1);
        return user;
    }
    public Vector<User> users() throws Exception {
        Vector<User> users = new Vector<User>();
        users.add(new User("Michal", "Plameňák", "777222111", "random@something.org"));
        users.add(new User("Petr", "Kůň"));
        users.add(new User("Martin", "Salamini", null, "some@address.com", "meloun"));
        users.add(new User("Martin", "Salamini", "+420423458932"));
        for (int i = 1; i <= users.size(); ++i) users.get(i-1).setId(i);
        return users;
    }

    public void addRole(int userId, int roleId) throws Exception {
    }
    public void deleteRole(int userId, int roleId) throws Exception {
    }
    public Vector<UserRole> roles(int userId) throws Exception {
        Vector<UserRole> userRoles = new Vector<UserRole>();
        userRoles.add(new UserRole(1, 1));
        return userRoles;
    }

    public void addSchedule(int userId, int scheduleTypeId, Schedule schedule) throws Exception {
        schedule.setId(5);
        schedule.setUserId(userId);
        schedule.setScheduleTypeId(scheduleTypeId);
    }
    public void editSchedule(int scheduleId, Schedule schedule) throws Exception {
        schedule.setId(scheduleId);
    }
    public void deleteSchedule(int scheduleId) throws Exception {
    }
    public Schedule schedule(int scheduleId) throws Exception {
        Schedule schedule = new Schedule(1, 2, new Date(116, 10, 2), null, 3);
        schedule.setId(scheduleId);
        return schedule;
    }
    // returns null if there is no schedule for this day
    public Schedule currentSchedule(int userId) throws Exception {
        Schedule schedule = new Schedule(userId, 2, new Date(116, 10, 2), null, 3);
        schedule.setId(2);
        return schedule;
    }
    // returns the last schedule, even if not current, or null if the user never had a schedule
    public Schedule lastSchedule(int userId) throws Exception {
        Schedule schedule = new Schedule(userId, 1, new Date(116, 10, 2), new Date(116, 10, 10), 1);
        schedule.setId(4);
        return schedule;
    }
    public Vector<Schedule> SchedulesForPeriod(int userId, Date from, Date to) throws Exception {
        if (from == null || to == null) throw new Exception("From nor to date must be null.");
        if (from.after(to)) throw new Exception("From date cannot be after to date.");
        Vector<Schedule> schedules = new Vector<Schedule>();
        schedules.add(new Schedule(userId, 1, from, to, 1));
        schedules.add(new Schedule(userId, 1, to, null, 1));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i-1).setId(i);
        return schedules;
    }
    public Vector<Schedule> schedules(int userId) throws Exception {
        Vector<Schedule> schedules = new Vector<Schedule>();
        schedules.add(new Schedule(userId, 1, new Date(116, 10, 2), new Date(116, 10, 10), 1));
        schedules.add(new Schedule(userId, 1, new Date(116, 10, 11), null, 4));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i-1).setId(i);
        return schedules;
    }

    public void addExceptionInSchedule(int scheduleId, ExceptionInSchedule exceptionInSchedule) throws Exception {
        exceptionInSchedule.setId(4);
        exceptionInSchedule.setScheduleId(scheduleId);
        for (int i = 1; i <= exceptionInSchedule.getShifts().size(); ++i)
            exceptionInSchedule.getShifts().get(i-1).setId(i);
    }
    public void editExceptionInSchedule(int exceptionInScheduleId, ExceptionInSchedule exceptionInSchedule) throws Exception {
        exceptionInSchedule.setId(exceptionInScheduleId);
    }
    public void deleteExceptionInSchedule(int exceptionInScheduleId) throws Exception {
    }
    public void addExceptionShift(int exceptionInScheduleId, ExceptionShift exceptionShift) throws Exception {
        exceptionShift.setId(8);
        exceptionShift.setExceptionInScheduleId(exceptionInScheduleId);
    }
    public void editExceptionShift(int exceptionShiftId, ExceptionShift exceptionShift) throws Exception {
        exceptionShift.setId(exceptionShiftId);
    }
    public void deleteExceptionShift(int exceptionShiftId) throws Exception {
    }
    public ExceptionInSchedule exceptionInSchedule(int exceptionInScheduleId) throws Exception {
        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(new Date(116, 10, 4), 1, "Need to finish some stuff.");
        Vector<ExceptionShift> shifts = new Vector<ExceptionShift>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), exceptionInScheduleId, "Staff meating"));
        shifts.add(new ExceptionShift(new Time(9, 0, 0), new Time(2, 0, 0), exceptionInScheduleId, "Management meating"));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), exceptionInScheduleId));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i-1).setId(i);
        exceptionInSchedule.setId(exceptionInScheduleId);
        exceptionInSchedule.setShifts(shifts);
        return exceptionInSchedule;
    }
    public ExceptionInSchedule exceptionInScheduleForDate(int scheduleId, Date date) throws Exception {
        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(date, 1, "Need to finish some stuff.");
        Vector<ExceptionShift> shifts = new Vector<ExceptionShift>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1, "Staff meating"));
        shifts.add(new ExceptionShift(new Time(9, 0, 0), new Time(2, 0, 0), 1, "Management meating"));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i-1).setId(i);
        exceptionInSchedule.setId(1);
        exceptionInSchedule.setShifts(shifts);
        return exceptionInSchedule;
    }
    public Vector<ExceptionInSchedule> exceptionInScheduleForPeriod(int scheduleId, Date from, Date to) throws Exception {
        Vector<ExceptionInSchedule> exceptions = new Vector<ExceptionInSchedule>();
        ExceptionInSchedule exception = new ExceptionInSchedule(from, 1);
        Vector<ExceptionShift> shifts = new Vector<ExceptionShift>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1));
        shifts.add(new ExceptionShift(new Time(9, 0, 0), new Time(2, 0, 0), 1));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1, "Management meating"));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i-1).setId(i);
        exception.setId(1);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds first exception in schedule
        exception = new ExceptionInSchedule(to, 1, "Need to finish some stuff.");
        shifts = new Vector<ExceptionShift>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1, "Staff meating"));
        shifts.add(new ExceptionShift(new Time(10, 0, 0), new Time(2, 0, 0), 1, "Management meating"));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1));
        for (int i = 4; i <= shifts.size(); ++i) shifts.get(i-4).setId(i);
        exception.setId(1);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds second exception in schedule
        return exceptions;
    }
    public Vector<ExceptionInSchedule> exceptionInSchedules(int scheduleId) throws Exception {
        Vector<ExceptionInSchedule> exceptions = new Vector<ExceptionInSchedule>();
        ExceptionInSchedule exception = new ExceptionInSchedule(new Date(116, 10, 4), 1);
        Vector<ExceptionShift> shifts = new Vector<ExceptionShift>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1));
        shifts.add(new ExceptionShift(new Time(10, 0, 0), new Time(2, 0, 0), 1));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1, "Staff meating"));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i-1).setId(i);
        exception.setId(1);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds first exception in schedule
        exception = new ExceptionInSchedule(new Date(116, 10, 8), 1, "Need to finish some stuff.");
        shifts = new Vector<ExceptionShift>(); // empty = no work this day
        exception.setId(1);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds second exception in schedule
        return exceptions;
    }

    public WorkDay shiftsForDate(int userId, Date date) throws Exception {
        WorkDay workDay = new WorkDay(date,
                new ScheduleShift("2. noční", new Time(22, 0, 0),
                        new Time(8, 0, 0), 2, 5, "Kdo usne, ma padaka!"));
        return workDay;
    }
    public Vector<WorkDay> shiftsForPeriod(int userId, Date from, Date to) throws Exception {
        if (from == null || to == null) throw new Exception("From nor to date must be null.");
        if (from.after(to)) throw new Exception("From date cannot be after to date.");
        Vector<WorkDay> workDays = new Vector<WorkDay>();
        int count = (int)(to.getTime() - from.getTime()) / (60*60*24*1000) + 1;
        Date tmp = from;
        for (int i = 0; i < count; ++i) {
            switch(new Random().nextInt(5)) {
                case 0:
                    workDays.add(new WorkDay(tmp, new ScheduleShift("2. noční", new Time(22, 0, 0),
                            new Time(8, 0, 0), 2, 6, "Kdo usne, má padáka!"),
                            new ExceptionShift(new Time(8, 0, 0), new Time(4, 0, 0), 1, "Uteču dřív.")));
                    break;
                case 1:
                    workDays.add(new WorkDay(tmp, new ScheduleShift("2. odpolední", new Time(14, 0, 0),
                            new Time(8, 0, 0), 1, 4, "Kdo usne, má padáka!"),
                            new ExceptionShift(new Time(15, 0, 0), new Time(9, 0, 0), 1)));
                    break;
                case 2:
                    workDays.add(new WorkDay(tmp, new ScheduleShift("1. ranní", new Time(6, 0, 0),
                            new Time(8, 0, 0), 2, 1)));
                    break;
                case 3:
                    workDays.add(new WorkDay(tmp, new ScheduleShift("1. odpolední", new Time(14, 0, 0),
                            new Time(8, 0, 0), 1, 3)));
                    break;
                default:
                    workDays.add(new WorkDay(tmp));
            }
            tmp.setTime(tmp.getTime() + 24*60*60*1000);
        }
        return workDays;
    }
}
