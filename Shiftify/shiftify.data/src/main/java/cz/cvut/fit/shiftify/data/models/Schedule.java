package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.joda.time.LocalDate;

import cz.cvut.fit.shiftify.data.DaoConverters.LocalDateToStringConverter;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "Schedule",
        generateConstructors = false,
        generateGettersSetters = false,
        indexes = {@Index(name = "Unique_Schedule_UserScheduleTypeFrom", unique = true, value = "userId,scheduleTypeId,from")})
public class Schedule {
    // Columns
    @Id(autoincrement = true)
    @Property(nameInDb = "Id")
    protected Long id;
    @NotNull
    @Property(nameInDb = "UserId")
    protected Long userId;
    @NotNull
    @Property(nameInDb = "ScheduleTypeId")
    protected Long scheduleTypeId;
    @NotNull
    @Property(nameInDb = "From")
    @Convert(converter = LocalDateToStringConverter.class, columnType = String.class)
    protected LocalDate from;
    @Property(nameInDb = "To")
    @Convert(converter = LocalDateToStringConverter.class, columnType = String.class)
    protected LocalDate to;
    @NotNull
    @Property(nameInDb = "StartingDayOfScheduleCycle")
    protected Integer startingDayOfScheduleCycle;

    // Relationships
    @ToOne(joinProperty = "userId")
    protected User user;
    @ToOne(joinProperty = "scheduleTypeId")
    protected ScheduleType scheduleType;

    // Constructors
    public Schedule() {
    }

    public Schedule(@NotNull Long userId, @NotNull Long scheduleTypeId, @NotNull LocalDate from,
                    LocalDate to, @NotNull Integer startingDayOfScheduleCycle) {
        this(null, userId, scheduleTypeId, from, to, startingDayOfScheduleCycle);
    }

    public Schedule(Long id, @NotNull Long userId, @NotNull Long scheduleTypeId, @NotNull LocalDate from,
                    LocalDate to, @NotNull Integer startingDayOfScheduleCycle) {
        this.id = id;
        this.userId = userId;
        this.scheduleTypeId = scheduleTypeId;
        this.from = from;
        this.to = to;
        this.startingDayOfScheduleCycle = startingDayOfScheduleCycle;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleTypeId() {
        return this.scheduleTypeId;
    }

    public void setScheduleTypeId(Long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Integer getStartingDayOfScheduleCycle() {
        return this.startingDayOfScheduleCycle;
    }

    public void setStartingDayOfScheduleCycle(Integer startingDayOfScheduleCycle) {
        this.startingDayOfScheduleCycle = startingDayOfScheduleCycle;
    }

    // GreenDAO generated attributes
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1493574644)
    private transient ScheduleDao myDao;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 128607475)
    private transient Long scheduleType__resolvedKey;

    // GreenDAO generated methods

    /**
     * To-one relationship, resolved on first access.
     */
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
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

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 118813034)
    public ScheduleType getScheduleType() {
        Long __key = this.scheduleTypeId;
        if (scheduleType__resolvedKey == null || !scheduleType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleTypeDao targetDao = daoSession.getScheduleTypeDao();
            ScheduleType scheduleTypeNew = targetDao.load(__key);
            synchronized (this) {
                scheduleType = scheduleTypeNew;
                scheduleType__resolvedKey = __key;
            }
        }
        return scheduleType;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1453467165)
    public void setScheduleType(@NotNull ScheduleType scheduleType) {
        if (scheduleType == null) {
            throw new DaoException("To-one property 'scheduleTypeId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.scheduleType = scheduleType;
            scheduleTypeId = scheduleType.getId();
            scheduleType__resolvedKey = scheduleTypeId;
        }
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
    @Generated(hash = 502317300)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScheduleDao() : null;
    }
}
