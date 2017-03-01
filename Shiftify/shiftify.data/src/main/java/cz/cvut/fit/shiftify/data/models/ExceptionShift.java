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
import org.joda.time.LocalTime;
import org.joda.time.Period;

import cz.cvut.fit.shiftify.data.DaoConverters.LocalTimeToStringConverter;
import cz.cvut.fit.shiftify.data.DaoConverters.PeriodToStringConverter;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "ExceptionShift",
        createInDb = true,
        generateConstructors = false,
        generateGettersSetters = false,
        indexes = { @Index(name = "Unique_ExceptionShift_FromSchedule", unique = true, value = "from,exceptionInScheduleId") })
public class ExceptionShift extends Shift {
    // Columns
    @Id
    @Property(nameInDb = "Id")
    protected Long id;
    @NotNull
    @Property(nameInDb = "From")
    @Convert(converter = LocalTimeToStringConverter.class, columnType = String.class)
    protected LocalTime from;
    @NotNull
    @Property(nameInDb = "Duration")
    @Convert(converter = PeriodToStringConverter.class, columnType = String.class)
    protected Period duration;
    @NotNull
    @Property(nameInDb = "ExceptionInScheduleId")
    protected Long exceptionInScheduleId;
    @NotNull
    @Property(nameInDb = "IsWorking")
    protected Boolean isWorking;
    @Property(nameInDb = "Description")
    protected String description;

    // Relationships
    @ToOne(joinProperty = "exceptionInScheduleId")
    protected ExceptionInSchedule exceptionInSchedule;

    // Constructors
    public ExceptionShift() {
    }

    public ExceptionShift(@NotNull LocalTime from, @NotNull Period duration,
                          Boolean isWorking) {
        this(null, from, duration, isWorking, null);
    }

    public ExceptionShift(@NotNull LocalTime from, @NotNull Period duration,
                          @NotNull Boolean isWorking, String description) {
        this(null, from, duration, isWorking, description);
    }

    public ExceptionShift(Long id, @NotNull LocalTime from, @NotNull Period duration,
                          @NotNull Boolean isWorking, String description) {
        super(id, from, duration, description);
        this.isWorking = isWorking;
    }

    // Getters and setters
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public LocalTime getFrom() {
        return from;
    }

    @Override
    public void setFrom(LocalTime from) {
        this.from = from;
    }

    @Override
    public Period getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Period duration) {
        this.duration = duration;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getExceptionInScheduleId() {
        return exceptionInScheduleId;
    }
    public void setExceptionInScheduleId(Long exceptionInScheduleId) {
        this.exceptionInScheduleId = exceptionInScheduleId;
    }
    public Boolean getIsWorking() {
        return isWorking;
    }
    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
    }

    @Override
    public String getName() {
        return "";
    }

    // GreenDAO generated attributes
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1850318261)
    private transient ExceptionShiftDao myDao;
    @Generated(hash = 46206390)
    private transient Long exceptionInSchedule__resolvedKey;

    // GreenDAO generated methods
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 60198533)
    public ExceptionInSchedule getExceptionInSchedule() {
        Long __key = this.exceptionInScheduleId;
        if (exceptionInSchedule__resolvedKey == null || !exceptionInSchedule__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExceptionInScheduleDao targetDao = daoSession.getExceptionInScheduleDao();
            ExceptionInSchedule exceptionInScheduleNew = targetDao.load(__key);
            synchronized (this) {
                exceptionInSchedule = exceptionInScheduleNew;
                exceptionInSchedule__resolvedKey = __key;
            }
        }
        return exceptionInSchedule;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 712631090)
    public void setExceptionInSchedule(@NotNull ExceptionInSchedule exceptionInSchedule) {
        if (exceptionInSchedule == null) {
            throw new DaoException("To-one property 'exceptionInScheduleId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.exceptionInSchedule = exceptionInSchedule;
            exceptionInScheduleId = exceptionInSchedule.getId();
            exceptionInSchedule__resolvedKey = exceptionInScheduleId;
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
    @Generated(hash = 1664599110)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getExceptionShiftDao() : null;
    }
}