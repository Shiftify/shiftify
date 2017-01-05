package cz.cvut.fit.shiftify.data.managers;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.cvut.fit.shiftify.data.App;
import cz.cvut.fit.shiftify.data.DaoSession;
import cz.cvut.fit.shiftify.data.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.Role;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.data.ScheduleShift;
import cz.cvut.fit.shiftify.data.Shift;
import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.UserDao;


/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class UserManager {

    private UserDao mUserDao;

    public UserManager() {
        DaoSession daoSession = App.getNewDaoSession();
        mUserDao = daoSession.getUserDao();
    }

    /**
     * Adds user, updates the instance afterwards (sets the id).
     */
    public void add(User user) throws Exception {
        mUserDao.insert(user);
//        user.setId(1L);
    }

    /**
     * Edits user, user has to have an id.
     */
    public void edit(User user) throws Exception {
    }

    /**
     * Deletes user that has id equal to userId.
     */
    public void delete(long userId) throws Exception {
        mUserDao.deleteByKey(userId);
    }

    /**
     * Gets user that has id equal to userId.
     */
    public User user(long userId) throws Exception {
//        User user = new User("Michal", "Plameňák", "777222111", "random@something.org");
//        user.setId(1L);
//        return user;

        return mUserDao.load(userId);
    }

    /**
     * Gets all users.
     */

    public static void initUsers() {
        UserDao userDao = App.getNewDaoSession().getUserDao();
        if (userDao.loadAll().size() < 8) {
            userDao.deleteAll();
            List<User> users = new ArrayList<>();
            users.add(new User("Michal", "Plameňák", "777222111", "random@something.org"));
            users.add(new User("Petr", "Kůň"));
            users.add(new User("Martin", "Salamini", null, "some@address.com", "meloun"));
            users.add(new User("Martin", "Salamini", "+420423458932"));
            users.add(new User("Adam", "Moron", "+420423458932"));
            users.add(new User("Jaromir", "Jagr", "+420423458932"));
            users.add(new User("Nekdo", "Nekdovic", "+420423458932"));
            users.add(new User("Nikdo", "Kdokolic", "+420427458932"));
            for (User u : users) {
                userDao.insert(u);
            }
        }
    }

    public List<User> users() throws Exception {
//        List<User> users = new ArrayList<>();
//        users.add(new User("Michal", "Plameňák", "777222111", "random@something.org"));
//        users.add(new User("Petr", "Kůň"));
//        users.add(new User("Martin", "Salamini", null, "some@address.com", "meloun"));
//        users.add(new User("Martin", "Salamini", "+420423458932"));
//        users.add(new User("Adam", "Moron", "+420423458932"));
//        users.add(new User("Jaromir", "Jagr", "+420423458932"));
//        users.add(new User("Nekdo", "Nekdovic", "+420423458932"));
//        users.add(new User("Nikdo", "Kdokolic", "+420427458932"));
//        for (int i = 1; i <= users.size(); ++i) users.get(i - 1).setId(Long.valueOf(i));
//        return users;
        return mUserDao.loadAll();
    }

    /**
     * Adds a relation between a user and a role.
     */
    public void addRole(long userId, long roleId) throws Exception {
    }

    /**
     * Deletes a relation between a user and a role.
     */
    public void deleteRole(long userId, long roleId) throws Exception {
    }

    /**
     * Gets all the roles that a user has.
     */
    public List<Role> roles(long userId) throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("administrator"));
        return roles;
    }

    /**
     * Adds a schedule to a user. This schedule needs to have a value in userId and scheduleTypeId.
     */
    public void addSchedule(Schedule schedule) throws Exception {
        schedule.setId(5);
    }

    /**
     * Edits a schedule. This schedule instance needs to have an id.
     */
    public void editSchedule(Schedule schedule) throws Exception {
    }

    /**
     * Deletes a schedule that has id equal to scheduleId.
     */
    public void deleteSchedule(int scheduleId) throws Exception {
    }

    /**
     * Gets schedule that has id equal to scheduleId.
     */
    public Schedule schedule(long scheduleId) throws Exception {
        Schedule schedule = new Schedule(new Long(1), 2, new Date(116, 10, 2), null, 3);
        schedule.setId(scheduleId);
        return schedule;
    }

    /**
     * Gets schedule (of a user), that is current = schedule.getFrom() <= today <= schedule.getTo().
     * Return null, if no schedule has met the requirements.
     */
    public Schedule currentSchedule(long userId) throws Exception {
        Schedule schedule = new Schedule(userId, 2, new Date(116, 10, 2), null, 3);
        schedule.setId(2);
        return schedule;
    }

    /**
     * Gets schedule (of a user), that has the greatest expiration date = schedule.getTo().
     */
    // returns the last schedule, even if not current, or null if the user never had a schedule
    public Schedule lastSchedule(long userId) throws Exception {
        Schedule schedule = new Schedule(userId, 1, new Date(116, 10, 2), new Date(116, 10, 10), 1);
        schedule.setId(4);
        return schedule;
    }

    /**
     * Gets schedules that are current in some of the dates between from and to.
     */
    public List<Schedule> SchedulesForPeriod(long userId, Date from, Date to) throws Exception {
        if (from == null || to == null) throw new Exception("From nor to date must be null.");
        if (from.after(to)) throw new Exception("From date cannot be after to date.");
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule(userId, 1, from, to, 1));
        schedules.add(new Schedule(userId, 1, to, null, 1));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i - 1).setId(i);
        return schedules;
    }

    /**
     * Gets all schedules of a user.
     */
    public List<Schedule> schedules(long userId) throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule(userId, 1, new Date(116, 10, 2), new Date(116, 10, 10), 1));
        schedules.add(new Schedule(userId, 1, new Date(116, 10, 11), null, 4));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i - 1).setId(i);
        return schedules;
    }

    /**
     * Adds an exceptionInSchedule. This instance needs to have a scheduleId
     * and a list of exceptionSchedules.
     */
    public void addExceptionInSchedule(long scheduleId, ExceptionInSchedule exceptionInSchedule) throws Exception {
        exceptionInSchedule.setId(new Long(4));
        exceptionInSchedule.setScheduleId(scheduleId);
        for (int i = 1; i <= exceptionInSchedule.getShifts().size(); ++i)
            exceptionInSchedule.getShifts().get(i - 1).setId(i);
    }

    /**
     * Edits an exceptionInSchedule. This instance needs to have an id.
     */
    public void editExceptionInSchedule(ExceptionInSchedule exceptionInSchedule) throws Exception {
    }

    /**
     * Deletes an exceptionInSchedule with an id equal to exceptionInScheduleId.
     */
    public void deleteExceptionInSchedule(long exceptionInScheduleId) throws Exception {
    }

    /**
     * Gets an ExceptionInSchedule that has an id equal to exceptionInScheduleId.
     */
    public ExceptionInSchedule exceptionInSchedule(long exceptionInScheduleId) throws Exception {
        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(new Date(116, 10, 4), 1L, "Need to finish some stuff.");
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), exceptionInScheduleId, true, "Staff meating"));
        shifts.add(new ExceptionShift(new Time(9, 0, 0), new Time(2, 0, 0), exceptionInScheduleId, true, "Management meating"));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), exceptionInScheduleId, true));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i - 1).setId(i);
        exceptionInSchedule.setId(exceptionInScheduleId);
        exceptionInSchedule.setShifts(shifts);
        return exceptionInSchedule;
    }

    /**
     * Gets an ExceptionInSchedule of a schedule that has a date set to the date in parameter.
     */
    public ExceptionInSchedule exceptionInScheduleForDate(int scheduleId, Date date) throws Exception {
        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(date, 1L, "Need to finish some stuff.");
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1L, true, "Staff meating"));
        shifts.add(new ExceptionShift(new Time(9, 0, 0), new Time(2, 0, 0), 1L, true, "Management meating"));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1L, true));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i - 1).setId(i);
        exceptionInSchedule.setId(1L);
        exceptionInSchedule.setShifts(shifts);
        return exceptionInSchedule;
    }

    /**
     * Gets a list of exceptionInSchedule of a user with a date between from and to.
     */
    public List<ExceptionInSchedule> exceptionInScheduleForPeriod(int userId, Date from, Date to) throws Exception {
        List<ExceptionInSchedule> exceptions = new ArrayList<>();
        ExceptionInSchedule exception = new ExceptionInSchedule(from, 1L);
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1L, true));
        shifts.add(new ExceptionShift(new Time(9, 0, 0), new Time(2, 0, 0), 1L, true));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1L, true, "Management meating"));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i - 1).setId(i);
        exception.setId(1L);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds first exception in schedule
        exception = new ExceptionInSchedule(to, 1L, "Need to finish some stuff.");
        shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1L, true, "Staff meating"));
        shifts.add(new ExceptionShift(new Time(10, 0, 0), new Time(2, 0, 0), 1L, true, "Management meating"));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1L, true));
        for (int i = 4; i <= shifts.size(); ++i) shifts.get(i - 4).setId(i);
        exception.setId(1L);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds second exception in schedule
        return exceptions;
    }

    /**
     * Gets a list of exceptionInSchedule of a schedule.
     */
    public List<ExceptionInSchedule> exceptionInSchedules(int scheduleId) throws Exception {
        List<ExceptionInSchedule> exceptions = new ArrayList<>();
        ExceptionInSchedule exception = new ExceptionInSchedule(new Date(116, 10, 4), 1L);
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(new Time(6, 0, 0), new Time(2, 0, 0), 1L, true));
        shifts.add(new ExceptionShift(new Time(10, 0, 0), new Time(2, 0, 0), 1L, true));
        shifts.add(new ExceptionShift(new Time(13, 0, 0), new Time(5, 0, 0), 1L, true, "Staff meating"));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i - 1).setId(i);
        exception.setId(1L);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds first exception in schedule
        exception = new ExceptionInSchedule(new Date(116, 10, 8), 1L, "Need to finish some stuff.");
        shifts = new ArrayList<>(); // empty = no work this day
        exception.setId(1L);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds second exception in schedule
        return exceptions;
    }

    /**
     * Gets a workDay of a user. This workDay can have a scheduleShift and if it has,
     * it can also have an exceptionShift. This workDay has a date given as parameter.
     */
    public WorkDay shiftsForDate(int userId, Date date) throws Exception {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("2. noční", new Time(22, 0, 0), new Time(8, 0, 0), 2, 5,
                "Kdo usne, ma padaka!"));
        WorkDay workDay = new WorkDay(date, shifts);
        return workDay;
    }

    /**
     * Gets a list of workDays of a user. These workDays can have a scheduleShift and those that do,
     * can also have an exceptionShift. These workDays have dates between from and to, for each
     * date in the period there is a workDay in the returned list.
     */
    public List<WorkDay> shiftsForPeriod(int userId, Date from, Date to) throws Exception {
        if (from == null || to == null) throw new Exception("From nor to date must be null.");
        if (from.after(to)) throw new Exception("From date cannot be after to date.");
        List<WorkDay> workDays = new ArrayList<>();
        int count = (int) (to.getTime() - from.getTime()) / (60 * 60 * 24 * 1000) + 1;
        Date tmp = from;
        List<Shift> list;
        for (int i = 0; i < count; ++i) {
            list = new ArrayList<>();
            switch (new Random().nextInt(5)) {
                case 0:
                    list.add(new ExceptionShift(new Time(8, 0, 0), new Time(4, 0, 0), 1L, true, "Uteču dřív."));
                    list.add(new ExceptionShift(new Time(22, 0, 0), new Time(2, 0, 0), 1L, false, "Uteču dřív."));
                    workDays.add(new WorkDay(tmp, list));
                    break;
                case 1:
                    list.add(new ScheduleShift("2. odpolední", new Time(14, 0, 0),
                            new Time(1, 0, 0), 1, 4, "Kdo usne, má padáka!"));
                    list.add(new ExceptionShift(new Time(15, 0, 0), new Time(2, 0, 0), 1L, false));
                    list.add(new ScheduleShift("2. odpolední", new Time(17, 0, 0),
                            new Time(5, 0, 0), 1, 4, "Kdo usne, má padáka!"));
                    list.add(new ExceptionShift(new Time(22, 0, 0), new Time(2, 0, 0), 5L, true));
                    workDays.add(new WorkDay(tmp, list));
                    break;
                case 2:
                    list.add(new ScheduleShift("1. ranní", new Time(6, 0, 0),
                            new Time(8, 0, 0), 2, 1));
                    workDays.add(new WorkDay(tmp, list));
                    break;
                case 3:
                    list.add(new ScheduleShift("1. odpolední", new Time(14, 0, 0),
                            new Time(8, 0, 0), 1, 3));
                    workDays.add(new WorkDay(tmp, list));
                    break;
                default:
                    workDays.add(new WorkDay(tmp));
            }
            tmp.setTime(tmp.getTime() + 24 * 60 * 60 * 1000);
        }
        return workDays;
    }
}
