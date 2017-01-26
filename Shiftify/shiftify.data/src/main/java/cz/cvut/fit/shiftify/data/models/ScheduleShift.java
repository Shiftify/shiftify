package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.sql.Time;
import java.util.GregorianCalendar;

import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_Time_Converter;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "ScheduleShift",
        createInDb = true,
        generateConstructors = false,
        generateGettersSetters = false,
        indexes = { @Index(name = "Unique_ScheduleShift_FromSchedule", unique = true, value = "from,scheduleTypeId") })
public class ScheduleShift extends Shift {
    // Columns
    @Id(autoincrement = true)
    @Property(nameInDb = "Id")
    protected Long id;
    @NotNull
    @Property(nameInDb = "ScheduleTypeId")
    protected Long scheduleTypeId;
    @NotNull
    @Property(nameInDb = "From")
    @Convert(converter = GregCal_Time_Converter.class, columnType = String.class)
    protected GregorianCalendar from;
    @NotNull
    @Property(nameInDb = "Duration")
    @Convert(converter = GregCal_Time_Converter.class, columnType = String.class)
    protected GregorianCalendar duration;
    @NotNull
    @Property(nameInDb = "Name")
    protected String name;
    @NotNull
    @Property(nameInDb = "DayOfScheduleCycle")
    protected Integer dayOfScheduleCycle;
    @Property(nameInDb = "Description")
    protected String description;

    // Relationships
    @ToOne(joinProperty = "scheduleTypeId")
    protected ScheduleType scheduleType;

    // Constructors
    public ScheduleShift() {
        this(null, null, null, null, null, null, null);
    }
    public ScheduleShift(@NotNull String name, @NotNull GregorianCalendar from, @NotNull GregorianCalendar duration,
                         @NotNull Long scheduleTypeId, @NotNull Integer dayOfScheduleCycle) {
        this(null, name, from, duration, scheduleTypeId, dayOfScheduleCycle, null);
    }
    public ScheduleShift(@NotNull String name, @NotNull GregorianCalendar from, @NotNull GregorianCalendar duration,
                         @NotNull Long scheduleTypeId, @NotNull Integer dayOfScheduleCycle, String description) {
        this(null, name, from, duration, scheduleTypeId, dayOfScheduleCycle, description);
    }
    public ScheduleShift(Long id, @NotNull String name, @NotNull GregorianCalendar from, @NotNull GregorianCalendar duration,
                         @NotNull Long scheduleTypeId, @NotNull Integer dayOfScheduleCycle, String description) {
        super(id, from, duration, description);
        setName(name);
        setScheduleTypeId(scheduleTypeId);
        setDayOfScheduleCycle(dayOfScheduleCycle);
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getScheduleTypeId() {
        return scheduleTypeId;
    }
    public void setScheduleTypeId(Long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }
    public Integer getDayOfScheduleCycle() {
        return dayOfScheduleCycle;
    }
    public void setDayOfScheduleCycle(Integer dayOfScheduleCycle) {
        this.dayOfScheduleCycle = dayOfScheduleCycle;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public GregorianCalendar getFrom() {
        return from;
    }
    public void setFrom(GregorianCalendar from) {
        this.from = from;
    }
    public GregorianCalendar getDuration() {
        return duration;
    }
    public void setDuration(GregorianCalendar duration) {
        this.duration = duration;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // GreenDAO generated attributes
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1868701852)
    private transient ScheduleShiftDao myDao;
    @Generated(hash = 128607475)
    private transient Long scheduleType__resolvedKey;

    // GreenDAO generated methods
    /** To-one relationship, resolved on first access. */
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
    /** called by internal mechanisms, do not call yourself. */
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
    @Generated(hash = 1863737805)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScheduleShiftDao() : null;
    }
}
