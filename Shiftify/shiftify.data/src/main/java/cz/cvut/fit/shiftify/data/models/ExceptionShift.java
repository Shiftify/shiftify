package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.sql.Time;
import java.util.GregorianCalendar;
import java.util.List;

import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_Time_Converter;
import cz.cvut.fit.shiftify.data.models.Shift;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

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
    @Convert(converter = GregCal_Time_Converter.class, columnType = String.class)
    protected GregorianCalendar from;
    @NotNull
    @Property(nameInDb = "Duration")
    @Convert(converter = GregCal_Time_Converter.class, columnType = String.class)
    protected GregorianCalendar duration;
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
    public ExceptionShift() { this(null, null, null, null, null, null); }
    public ExceptionShift(@NotNull GregorianCalendar from, @NotNull GregorianCalendar duration,
                          @NotNull Long exceptionInScheduleId, Boolean isWorking) {
        this(null, from, duration, exceptionInScheduleId, isWorking, null);
    }
    public ExceptionShift(@NotNull GregorianCalendar from, @NotNull GregorianCalendar duration,
                          @NotNull Long exceptionInScheduleId, @NotNull Boolean isWorking, String description) {
        this(null, from, duration, exceptionInScheduleId, isWorking, description);
    }
    public ExceptionShift(Long id, @NotNull GregorianCalendar from, @NotNull GregorianCalendar duration,
                          @NotNull Long exceptionInScheduleId, @NotNull Boolean isWorking, String description) {
        super(id, from, duration, description);
        setExceptionInScheduleId(exceptionInScheduleId);
        setIsWorking(isWorking);
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Methods
    public boolean isExceptionShift() { return false; }

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