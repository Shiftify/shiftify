package cz.cvut.fit.shiftify.data.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import cz.cvut.fit.shiftify.data.App;
import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_Date_Converter;
import cz.cvut.fit.shiftify.data.Utilities;
import cz.cvut.fit.shiftify.data.models.DaoSession;
import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionInScheduleDao;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.ExceptionShiftDao;
import cz.cvut.fit.shiftify.data.models.Role;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.Shift;
import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.ScheduleDao;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.models.UserDao;
import cz.cvut.fit.shiftify.data.models.UserRoleDao;


/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class UserManager {

    private UserDao userDao;
    private UserRoleDao userRoleDao;
    private ScheduleDao scheduleDao;
    private ExceptionInScheduleDao exceptionInScheduleDao;
    private ExceptionShiftDao exceptionShiftDao;

    public UserManager() {
        DaoSession daoSession = App.getNewDaoSession();
        daoSession.clear();
        userDao = daoSession.getUserDao();
        userRoleDao = daoSession.getUserRoleDao();
        scheduleDao = daoSession.getScheduleDao();
        exceptionInScheduleDao = daoSession.getExceptionInScheduleDao();
        exceptionShiftDao = daoSession.getExceptionShiftDao();
    }

    /**
     * Adds user, and sets the id.
     */
    public void add(User user) throws Exception {
        userDao.insert(user);
    }

    /**
     * Edits user, user has to have an id.
     */
    public void edit(User user) throws Exception {
        userDao.update(user);
    }

    /**
     * Deletes user that has id equal to userId.
     */
    public void delete(long userId) throws Exception {
        userDao.deleteByKey(userId);
    }

    public void deleteAll() throws Exception {
        userDao.deleteAll();
    }

    /**
     * Gets user that has id equal to userId.
     */
    public User user(long userId) {
        return userDao.load(userId);
    }

    /**
     * Gets all all users.
     */
    public List<User> allUsers() {
        return userDao.loadAll();
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
        scheduleDao.insert(schedule);
    }

    /**
     * Edits a schedule. This schedule instance needs to have an id.
     */
    public void editSchedule(Schedule schedule) throws Exception {
        scheduleDao.update(schedule);
    }

    /**
     * Deletes a schedule that has id equal to scheduleId.
     */
    public void deleteSchedule(long scheduleId) throws Exception {
        scheduleDao.deleteByKey(scheduleId);
    }

    public void deleteScheduleAll() {
        scheduleDao.deleteAll();
    }

    /**
     * Gets schedule that has id equal to scheduleId.
     */
    public Schedule schedule(long scheduleId) {
        return scheduleDao.load(scheduleId);
    }

    /**
     * Gets schedule (of a user), that is current = schedule.getFrom() <= today <= schedule.getTo().
     * Return null, if no schedule has met the requirements.
     */
    public Schedule currentSchedule(long userId) throws Exception {
//        Schedule schedule = new Schedule(userId, 2, new Date(116, 10, 2), null, 3);
//        schedule.setId(2);
//        return schedule;
        User user = userDao.load(userId);
        Date today = Calendar.getInstance().getTime();
        for (Schedule schedule : user.getSchedules()) {
            if (schedule.getFrom().before(today) && (schedule.getTo() == null || schedule.getTo().after(today)))
                return schedule;
        }
        return null;
    }

    /**
     * Gets schedule (of a user), that has the greatest expiration date = schedule.getTo().
     */
    // returns the last schedule, even if not current, or null if the user never had a schedule
    public Schedule lastSchedule(long userId) throws Exception {
        Schedule schedule = new Schedule(userId, 1L, new GregorianCalendar(116, 10, 2), new GregorianCalendar(116, 10, 10), 1);
        schedule.setId(4L);
        return schedule;
    }

    /**
     * Gets schedules that are current in some of the dates between from and to.
     */
    public List<Schedule> SchedulesForPeriod(long userId, GregorianCalendar from, GregorianCalendar to) throws Exception {
        if (from == null || to == null) throw new Exception("From nor to date must be null.");
        if (from.after(to)) throw new Exception("From date cannot be after to date.");
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule(userId, 1L, from, to, 1));
        schedules.add(new Schedule(userId, 1L, to, null, 1));
        for (int i = 1; i <= schedules.size(); ++i) schedules.get(i - 1).setId((long) i);
        return schedules;
    }

    /**
     * Gets all schedules of a user.
     */
    public List<Schedule> schedules(long userId) throws Exception {
        return userDao.load(userId).getSchedules();
    }

    /**
     * Adds an exceptionInSchedule. This instance needs to have a scheduleId
     * and a list of exceptionSchedules.
     */
    public void addExceptionInSchedule(long scheduleId, ExceptionInSchedule exceptionInSchedule) throws Exception {
        exceptionInSchedule.setId(4L);
        exceptionInSchedule.setScheduleId(scheduleId);
        for (int i = 1; i <= exceptionInSchedule.getShifts().size(); ++i)
            exceptionInSchedule.getShifts().get(i - 1).setId((long) i);
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
        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(new GregorianCalendar(2016, 10, 4), 1L, 2L, "Need to finish some stuff.");
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(2, 0), true, "Staff meating"));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(9, 0), Utilities.GregCalFrom(2, 0), true, "Management meating"));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(13, 0), Utilities.GregCalFrom(5, 0), true));
        for (int i = 1; i <= shifts.size(); ++i) {
            shifts.get(i - 1).setId((long) i);
            shifts.get(i - 1).setExceptionInScheduleId(exceptionInScheduleId);
        }
        exceptionInSchedule.setId(exceptionInScheduleId);
        exceptionInSchedule.setShifts(shifts);
        return exceptionInSchedule;
    }

    /**
     * Gets an ExceptionInSchedule of a schedule that has a date set to the date in parameter.
     */
    public ExceptionInSchedule exceptionInScheduleForDate(int scheduleId, Date date) throws Exception {
        ExceptionInSchedule exceptionInSchedule = new ExceptionInSchedule(new GregorianCalendar(2016, 10, 4), 1L, 2L, "Need to finish some stuff.");
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(2, 0), true, "Staff meating"));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(9, 0), Utilities.GregCalFrom(2, 0), true, "Management meating"));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(13, 0), Utilities.GregCalFrom(5, 0), true));
        for (int i = 1; i <= shifts.size(); ++i) {
            shifts.get(i - 1).setId((long) i);
            shifts.get(i-1).setExceptionInScheduleId(1L);
        }
        exceptionInSchedule.setId(1L);
        //exceptionInSchedule.setShifts(shifts);
        return exceptionInSchedule;
    }

    /**
     * Gets a list of exceptionInSchedule of a user with a date between from and to.
     */
    public List<ExceptionInSchedule> exceptionInScheduleForPeriod(int userId, GregorianCalendar from, GregorianCalendar to) throws Exception {
        List<ExceptionInSchedule> exceptions = new ArrayList<>();
        ExceptionInSchedule exception = new ExceptionInSchedule(from, 1L);
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(2, 0), true));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(9, 0), Utilities.GregCalFrom(2, 0), true));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(13, 0), Utilities.GregCalFrom(5, 0), true, "Management meating"));
        for (int i = 1; i <= shifts.size(); ++i) {
            shifts.get(i - 1).setId((long) i);
            shifts.get(i-1).setExceptionInScheduleId(1L);
        }
        exception.setId(1L);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds first exception in schedule
        exception = new ExceptionInSchedule(to, 1L, null, "Need to finish some stuff.");
        shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(2, 0), true, "Staff meating"));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(10, 0), Utilities.GregCalFrom(2, 0), true, "Management meating"));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(13, 0), Utilities.GregCalFrom(5, 0), true));
        for (int i = 4; i <= shifts.size(); ++i) {
            shifts.get(i - 4).setId((long) i);
            shifts.get(i-4).setExceptionInScheduleId(1L);
        }
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
        ExceptionInSchedule exception = new ExceptionInSchedule(new GregorianCalendar(2016, 10, 4), 1L);
        List<ExceptionShift> shifts = new ArrayList<>();
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(2, 0), true));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(10, 0), Utilities.GregCalFrom(2, 0), true));
        shifts.add(new ExceptionShift(Utilities.GregCalFrom(13, 0), Utilities.GregCalFrom(5, 0), true, "Staff meating"));
        for (int i = 1; i <= shifts.size(); ++i) {
            shifts.get(i - 1).setId((long) i);
            shifts.get(i-1).setExceptionInScheduleId(1L);
        }
        exception.setId(1L);
        exception.setShifts(shifts);
        exceptions.add(exception); // adds first exception in schedule
        exception = new ExceptionInSchedule(new GregorianCalendar(2016, 10, 8), 1L, null, "Need to finish some stuff.");
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
    public WorkDay shiftsForDate(long userId, GregorianCalendar date) throws Exception {
        String dateStr = new GregCal_Date_Converter().convertToDatabaseValue(date);
        String prevDateStr = new GregCal_Date_Converter().convertToDatabaseValue(Utilities.GregCalPrevDay(date));
        List<Schedule> schedules = getSchedulesFor(userId, prevDateStr, dateStr);
        List<ExceptionInSchedule> exceptionInSchedules = getExceptionSchedulesFor(userId, dateStr, dateStr);
        return WorkDay.calculateWorkDays(date, schedules, exceptionInSchedules);
    }

    /**
     * Gets a list of workDays of a user. These workDays can have a scheduleShift and those that do,
     * can also have an exceptionShift. These workDays have dates between from and to, for each
     * date in the period there is a workDay in the returned list.
     */
    public List<WorkDay> shiftsForPeriod(long userId, GregorianCalendar from, GregorianCalendar to) throws Exception {
        String prevFromStr = new GregCal_Date_Converter().convertToDatabaseValue(Utilities.GregCalPrevDay(from));
        String fromStr = new GregCal_Date_Converter().convertToDatabaseValue(from);
        String toStr = new GregCal_Date_Converter().convertToDatabaseValue(to);
        List<Schedule> schedules = getSchedulesFor(userId, prevFromStr, toStr);
        List<ExceptionInSchedule> exceptionInSchedules = getExceptionSchedulesFor(userId, fromStr, toStr);
        return WorkDay.calculateWorkDays(from, to, schedules, exceptionInSchedules);
    }

    private List<Schedule> getSchedulesFor(long userId, String from, String to) {
        return scheduleDao.queryBuilder().where(
                scheduleDao.queryBuilder().and(
                        ScheduleDao.Properties.UserId.eq(userId),
                        ScheduleDao.Properties.From.le(to),
                        scheduleDao.queryBuilder().or(
                                ScheduleDao.Properties.To.isNull(),
                                ScheduleDao.Properties.To.ge(from)
                        )
                )
        ).list();
    }
    private List<ExceptionInSchedule> getExceptionSchedulesFor(long userId, String from, String to) {
        return exceptionInScheduleDao.queryBuilder().where(
            exceptionInScheduleDao.queryBuilder().and(
                ExceptionInScheduleDao.Properties.UserId.eq(userId),
                ExceptionInScheduleDao.Properties.Date.le(to),
                ExceptionInScheduleDao.Properties.Date.ge(from)
            )
        ).list();
    }

    public List<ExceptionShift> getAllExceptionShifts(long userId) {
        User user = userDao.load(userId);
        List<ExceptionShift> shifts = new ArrayList<>();
        for (ExceptionInSchedule e : user.getExceptionInSchedules()) {
            shifts.addAll(e.getShifts());
        }
        return shifts;
    }

    public ExceptionShift getExceptionShift(long exceptionId) throws Exception {
        return exceptionShiftDao.load(exceptionId);
    }
}
