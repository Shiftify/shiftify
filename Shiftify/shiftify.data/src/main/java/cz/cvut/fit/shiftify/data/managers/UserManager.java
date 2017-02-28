package cz.cvut.fit.shiftify.data.managers;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.App;
import cz.cvut.fit.shiftify.data.DaoConverters.LocalDateToStringConverter;
import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.WorkDayFactory;
import cz.cvut.fit.shiftify.data.models.DaoSession;
import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionInScheduleDao;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.ExceptionShiftDao;
import cz.cvut.fit.shiftify.data.models.Role;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleDao;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.models.UserDao;
import cz.cvut.fit.shiftify.data.models.UserRole;
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
        userRoleDao.insert(new UserRole(userId, roleId));
    }

    /**
     * Deletes a relation between a user and a role.
     */
    public void deleteRole(long userId, long roleId) throws Exception {
        userRoleDao.deleteInTx(
            userRoleDao.queryBuilder().where(
                userRoleDao.queryBuilder().and(
                    UserRoleDao.Properties.UserId.eq(userId),
                    UserRoleDao.Properties.RoleId.eq(roleId)
                )
            ).list()
        );
    }

    /**
     * Gets all the roles that a user has.
     */
    public List<Role> roles(long userId) throws Exception {
        return userDao.load(userId).getRoles();
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
        List<Schedule> schedules = SchedulesForPeriod(userId, LocalDate.now(), LocalDate.now());
        if (schedules.size() == 0)
            return null;
        return schedules.get(0);
    }

    /**
     * Gets schedule (of a user), that has the greatest expiration date = schedule.getTo().
     */
    // returns the last schedule, even if not current, or null if the user never had a schedule
    public Schedule lastSchedule(long userId) throws Exception {
        List<Schedule> schedules = scheduleDao.queryBuilder().where(
            ScheduleDao.Properties.UserId.eq(userId)
        ).orderDesc(ScheduleDao.Properties.From).list();
        if (schedules.size() == 0)
            return null;
        return schedules.get(0);
    }

    /**
     * Gets schedules that are current in some of the dates between from and to.
     */
    public List<Schedule> SchedulesForPeriod(long userId, LocalDate from, LocalDate to) throws Exception {
        return scheduleDao.queryBuilder().where(
            scheduleDao.queryBuilder().and(
                ScheduleDao.Properties.UserId.eq(userId),
                scheduleDao.queryBuilder().or(
                    ScheduleDao.Properties.From.le(LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(to)),
                    ScheduleDao.Properties.To.ge(LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(from))
                )
            )
        ).list();
    }

    /**
     * Gets all schedules of a user.
     */
    public List<Schedule> schedules(long userId) throws Exception {
        return scheduleDao.queryBuilder().where(
            ScheduleDao.Properties.UserId.eq(userId)
        ).list();
    }

    /**
     * Adds an exceptionInSchedule. This instance needs to have a scheduleId
     * and a list of exceptionSchedules.
     */
    public void addExceptionInSchedule(ExceptionInSchedule exceptionInSchedule) throws Exception {
        exceptionInScheduleDao.insert(exceptionInSchedule);
    }

    /**
     * Edits an exceptionInSchedule. This instance needs to have an id.
     */
    public void editExceptionInSchedule(ExceptionInSchedule exceptionInSchedule) throws Exception {
        exceptionInScheduleDao.update(exceptionInSchedule);
    }

    /**
     * Deletes an exceptionInSchedule with an id equal to exceptionInScheduleId.
     */
    public void deleteExceptionInSchedule(long exceptionInScheduleId) throws Exception {
        exceptionInScheduleDao.delete(exceptionInScheduleDao.load(exceptionInScheduleId));
    }

    /**
     * Gets an ExceptionInSchedule that has an id equal to exceptionInScheduleId.
     */
    public ExceptionInSchedule exceptionInSchedule(long exceptionInScheduleId) throws Exception {
        return exceptionInScheduleDao.load(exceptionInScheduleId);
    }

    /**
     * Gets an ExceptionInSchedule of a schedule that has a date set to the date in parameter.
     */
    public ExceptionInSchedule exceptionInScheduleForDate(int scheduleId, LocalDate date) throws Exception {
        List<ExceptionInSchedule> exceptionInSchedules = exceptionInScheduleDao.queryBuilder().where(
            exceptionInScheduleDao.queryBuilder().and(
                ExceptionInScheduleDao.Properties.ScheduleId.eq(scheduleId),
                ExceptionInScheduleDao.Properties.Date.eq(LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(date))
            )
        ).list();
        if (exceptionInSchedules.size() == 0)
            return null;
        return exceptionInSchedules.get(0);
    }

    /**
     * Gets a list of exceptionInSchedule of a user with a date between from and to.
     */
    public List<ExceptionInSchedule> exceptionInScheduleForPeriod(int userId, LocalDate from, LocalDate to) throws Exception {
        return exceptionInScheduleDao.queryBuilder().where(
            exceptionInScheduleDao.queryBuilder().and(
                ExceptionInScheduleDao.Properties.Date.ge(LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(from)),
                ExceptionInScheduleDao.Properties.Date.le(LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(to))
            )
        ).list();
    }

    /**
     * Gets a list of exceptionInSchedule of a schedule.
     */
    public List<ExceptionInSchedule> exceptionInSchedules(int scheduleId) throws Exception {
        return exceptionInScheduleDao.queryBuilder().where(ExceptionInScheduleDao.Properties.ScheduleId.eq(scheduleId)).list();
    }

    /**
     * Gets a workDay of a user. This workDay can have a scheduleShift and if it has,
     * it can also have an exceptionShift. This workDay has a date given as parameter.
     */
    public WorkDay shiftsForDate(long userId, LocalDate date) throws Exception {
        String dateStr = LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(date);
        String prevDateStr = LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(date.minusDays(1));
        List<Schedule> schedules = getSchedulesFor(userId, prevDateStr, dateStr);
        List<ExceptionInSchedule> exceptionInSchedules = getExceptionSchedulesFor(userId, dateStr, dateStr);

        WorkDayFactory factory = new WorkDayFactory(schedules, exceptionInSchedules);
        WorkDay day = factory.createWorkDay(date);
        return day;
    }

    /**
     * Gets a list of workDays of a user. These workDays can have a scheduleShift and those that do,
     * can also have an exceptionShift. These workDays have dates between from and to, for each
     * date in the period there is a workDay in the returned list.
     */
    public List<WorkDay> shiftsForPeriod(long userId, LocalDate from, LocalDate to) throws Exception {
        String prevFromStr = LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(from.minusDays(1));
        String fromStr = LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(from);
        String toStr =  LocalDateToStringConverter.INSTANCE.convertToDatabaseValue(to);
        List<Schedule> schedules = getSchedulesFor(userId, prevFromStr, toStr);
        List<ExceptionInSchedule> exceptionInSchedules = getExceptionSchedulesFor(userId, fromStr, toStr);

        WorkDayFactory factory = new WorkDayFactory(schedules, exceptionInSchedules);
        List<WorkDay> workDays = factory.createWorkDayList(from, to);

        return workDays;
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
