package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(generateConstructors = false, generateGettersSetters = false)
public class User {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    private String nickname;
    private String phoneNumber;
    private String email;
    private String picturePath;

/*    Useless for now
    @ToMany
    @JoinEntity(entity = UserRole.class,
            sourceProperty = "userId",
            targetProperty = "roleId")
    private List<Role> roles;
*/

    @ToMany(referencedJoinProperty = "userId")
    private List<Schedule> schedules;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    public User() {
    }

    public User(@NotNull String firstName, @NotNull String surname) {
        this(firstName, surname, null, null, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber) {
        this(firstName, surname, phoneNumber, null, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email) {
        this(firstName, surname, phoneNumber, email, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname) {
        this(firstName, surname, phoneNumber, email, nickname, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        super();
        setFirstName(firstName);
        setSurname(surname);
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPicturePath(picturePath);
    }

    public User(Long id, String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        setId(id);
        setFirstName(firstName);
        setSurname(surname);
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPicturePath(picturePath);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
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
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public String getFullNameNick() {
        String res = firstName + " ";
        if (nickname != null) {
            res += "\"" + nickname + "\" ";
        }
        res += surname;
        return res;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
