package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import cz.cvut.fit.shiftify.data.validator.PropertyType;
import cz.cvut.fit.shiftify.data.validator.ValidateProperty;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "User",
        generateConstructors = false,
        generateGettersSetters = false,
        indexes = { @Index(name = "Unique_User_Name", unique = true, value = "firstName,surname,nickname") })
public class User {
    // Columns
    @Id(autoincrement = true)
    @Property(nameInDb = "Id")
    private Long id;
    @NotNull
    @Property(nameInDb = "FirstName")
    private String firstName;
    @NotNull
    @Property(nameInDb = "Surname")
    private String surname;
    @NotNull
    @Property(nameInDb = "Nickname")
    private String nickname;
    @Property(nameInDb = "PhoneNumber")
    private String phoneNumber;
    @Property(nameInDb = "Email")
    private String email;
    @Property(nameInDb = "PicturePath")
    private String picturePath;

    // Relationships
    @ToMany
    @JoinEntity(entity = UserRole.class, sourceProperty = "userId", targetProperty = "roleId")
    @OrderBy(value = "name")
    private List<Role> roles;
    @ToMany(referencedJoinProperty = "userId")
    @OrderBy(value = "from DESC")
    private List<Schedule> schedules;
    @ToMany(referencedJoinProperty = "userId")
    @OrderBy(value = "date DESC")
    private List<ExceptionInSchedule> exceptionInSchedules;

    // Constructors
    public User() {
    }

    public User(@NotNull String firstName, @NotNull String surname) {
        this(null, firstName, surname, null, null, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber) {
        this(null, firstName, surname, phoneNumber, null, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email) {
        this(null, firstName, surname, phoneNumber, email, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname) {
        this(null, firstName, surname, phoneNumber, email, nickname, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        this(null, firstName, surname, phoneNumber, email, nickname, picturePath);
    }

    public User(Long id, @NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.picturePath = picturePath;
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return (this.nickname == null ? (this.nickname = "") : this.nickname);
    }

    public void setNickname(String nickname) {
        this.nickname = (nickname == null ? "" : nickname);
    }

    @ValidateProperty(propertyType = PropertyType.PHONE)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ValidateProperty(propertyType = PropertyType.MAIL)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    // Methods
    public String getFullNameWithNick() {
        String nick = getNickname();
        return getFirstName()
                + (nick.length() == 0 ? " " : " \"" + nick + "\" ")
                + getSurname();
    }

    // GreenDAO generated attributes
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    // GreenDAO generated methods
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 338367888)
    public List<Schedule> getSchedules() {
        if (schedules == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleDao targetDao = daoSession.getScheduleDao();
            List<Schedule> schedulesNew = targetDao._queryUser_Schedules(id);
            synchronized (this) {
                if (schedules == null) {
                    schedules = schedulesNew;
                }
            }
        }
        return schedules;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 283382071)
    public synchronized void resetSchedules() {
        schedules = null;
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
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 353725095)
    public List<Role> getRoles() {
        if (roles == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RoleDao targetDao = daoSession.getRoleDao();
            List<Role> rolesNew = targetDao._queryUser_Roles(id);
            synchronized (this) {
                if (roles == null) {
                    roles = rolesNew;
                }
            }
        }
        return roles;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 254386649)
    public synchronized void resetRoles() {
        roles = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1903500619)
    public List<ExceptionInSchedule> getExceptionInSchedules() {
        if (exceptionInSchedules == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExceptionInScheduleDao targetDao = daoSession.getExceptionInScheduleDao();
            List<ExceptionInSchedule> exceptionInSchedulesNew = targetDao._queryUser_ExceptionInSchedules(id);
            synchronized (this) {
                if (exceptionInSchedules == null) {
                    exceptionInSchedules = exceptionInSchedulesNew;
                }
            }
        }
        return exceptionInSchedules;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 217710396)
    public synchronized void resetExceptionInSchedules() {
        exceptionInSchedules = null;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
