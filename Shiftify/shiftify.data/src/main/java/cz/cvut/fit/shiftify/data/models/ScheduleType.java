package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(generateConstructors = false)
public class ScheduleType {

    @Id(autoincrement = true)
    protected Long id;

    @NotNull
    protected String name;

    @NotNull
    protected Integer daysOfScheduleCycle;
    protected String description;

    public ScheduleType() {
    }

    public ScheduleType(String name, Integer daysOfScheduleCycle) {
        this(name, daysOfScheduleCycle, null);
    }

    public ScheduleType(String name, Integer daysOfScheduleCycle, String description) {
        setName(name);
        setDaysOfScheduleCycle(daysOfScheduleCycle);
        setDescription(description);
    }

    public ScheduleType(Long id, @NotNull String name, @NotNull Integer daysOfScheduleCycle,
                        String description) {
        this.id = id;
        this.name = name;
        this.daysOfScheduleCycle = daysOfScheduleCycle;
        this.description = description;
    }

    @ToMany(referencedJoinProperty = "scheduleTypeId")
    private List<ScheduleShift> shifts;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1056866477)
    private transient ScheduleTypeDao myDao;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDaysOfScheduleCycle() {
        return this.daysOfScheduleCycle;
    }

    public void setDaysOfScheduleCycle(Integer daysOfScheduleCycle) {
        this.daysOfScheduleCycle = daysOfScheduleCycle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShifts(List<ScheduleShift> shifts) {
        this.shifts = shifts;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1614590499)
    public List<ScheduleShift> getShifts() {
        if (shifts == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleShiftDao targetDao = daoSession.getScheduleShiftDao();
            List<ScheduleShift> shiftsNew = targetDao._queryScheduleType_Shifts(id);
            synchronized (this) {
                if (shifts == null) {
                    shifts = shiftsNew;
                }
            }
        }
        return shifts;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
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
    @Generated(hash = 632208713)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScheduleTypeDao() : null;
    }
}
