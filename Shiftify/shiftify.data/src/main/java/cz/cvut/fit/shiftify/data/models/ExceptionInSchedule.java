package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.DaoConverters.LocalDateToStringConverter;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "ExceptionInSchedule",
        generateGettersSetters = false,
        generateConstructors = false,
        indexes = { @Index(name = "Unique_ExceptionInSchedule_UserDate", unique = true, value = "userId,date") })
public class ExceptionInSchedule {
    // Columns
    @Id(autoincrement = true)
    @Property(nameInDb = "Id")
    protected Long id;
    @NotNull
    @Property(nameInDb = "UserId")
    protected Long userId;
    @Property(nameInDb = "ScheduleId")
    protected Long scheduleId;
    @NotNull
    @Property(nameInDb = "Date")
    @Convert(converter = LocalDateToStringConverter.class, columnType = String.class)
    protected LocalDate date;
    @Property(nameInDb = "Description")
    protected String description;

    // Relationships
    @ToOne(joinProperty = "userId")
    protected User user;
    @ToOne(joinProperty = "scheduleId")
    protected Schedule schedule;
    @ToMany(referencedJoinProperty = "exceptionInScheduleId")
    @OrderBy(value = "from")
    protected List<ExceptionShift> shifts;

    // Constructors
    public ExceptionInSchedule() {
        shifts = new ArrayList<>();
    }

    public ExceptionInSchedule(LocalDate date, Long userId) {
        this(null, date, userId, null, null);
    }

    public ExceptionInSchedule(LocalDate date, Long userId, Long scheduleId) {
        this(null, date, userId, scheduleId, null);
    }

    public ExceptionInSchedule(LocalDate date, Long userId, Long scheduleId, String description) {
        this(null, date, userId, scheduleId, description);
    }

    public ExceptionInSchedule(Long id, LocalDate date, Long userId, Long scheduleId, String description) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.description = description;
        shifts = new ArrayList<>();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addExceptionShift(ExceptionShift exceptionShift){
        shifts.add(exceptionShift);
    }

    // GreenDAO generated attributes
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1009773590)
    private transient ExceptionInScheduleDao myDao;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 1233392285)
    private transient Long schedule__resolvedKey;

    // GreenDAO generated methods
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 462495677)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException("To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userId = user.getId();
            user__resolvedKey = userId;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1337236917)
    public Schedule getSchedule() {
        Long __key = this.scheduleId;
        if (schedule__resolvedKey == null || !schedule__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleDao targetDao = daoSession.getScheduleDao();
            Schedule scheduleNew = targetDao.load(__key);
            synchronized (this) {
                schedule = scheduleNew;
                schedule__resolvedKey = __key;
            }
        }
        return schedule;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1716393727)
    public void setSchedule(Schedule schedule) {
        synchronized (this) {
            this.schedule = schedule;
            scheduleId = schedule == null ? null : schedule.getId();
            schedule__resolvedKey = scheduleId;
        }
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1323019868)
    public List<ExceptionShift> getShifts() {
        if (shifts == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExceptionShiftDao targetDao = daoSession.getExceptionShiftDao();
            List<ExceptionShift> shiftsNew = targetDao._queryExceptionInSchedule_Shifts(id);
            synchronized (this) {
                if (shifts == null) {
                    shifts = shiftsNew;
                }
            }
        }
        return shifts;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2027901155)
    public synchronized void resetShifts() {
        shifts = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1168379760)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getExceptionInScheduleDao() : null;
    }
}
