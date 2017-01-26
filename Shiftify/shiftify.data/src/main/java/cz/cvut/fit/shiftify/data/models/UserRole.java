package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "UserRole",
        generateConstructors = false,
        generateGettersSetters = false,
        indexes = { @Index(name = "Unique_UserRole_UserRole", unique = true, value = "userId,roleId")})
public class UserRole {
    // Columns
    @Id(autoincrement = true)
    @Property(nameInDb = "Id")
    protected Long id;
    @NotNull
    @Property(nameInDb = "UserId")
    protected Long userId;
    @NotNull
    @Property(nameInDb = "RoleId")
    protected Long roleId;

    // Relationships
    @ToOne(joinProperty = "userId")
    protected User user;
    @ToOne(joinProperty = "roleId")
    protected Role role;

    // Constructors
    public UserRole() {
        this(null, null, null);
    }
    public UserRole(@NotNull Long userId, @NotNull Long roleId) {
        this(null, userId, roleId);
    }
    public UserRole(Long id, @NotNull Long userId, @NotNull Long roleId) {
        setId(id);
        setUserId(userId);
        setRoleId(roleId);
    }

    // Getters and setters
    public Long getId() {
        return this.id;
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
    public Long getRoleId() {
        return this.roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    // GreenDAO generated attributes
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1114803083)
    private transient UserRoleDao myDao;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 312471022)
    private transient Long role__resolvedKey;

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
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userId = user.getId();
            user__resolvedKey = userId;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1948065557)
    public Role getRole() {
        Long __key = this.roleId;
        if (role__resolvedKey == null || !role__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RoleDao targetDao = daoSession.getRoleDao();
            Role roleNew = targetDao.load(__key);
            synchronized (this) {
                role = roleNew;
                role__resolvedKey = __key;
            }
        }
        return role;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1734660000)
    public void setRole(@NotNull Role role) {
        if (role == null) {
            throw new DaoException(
                    "To-one property 'roleId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.role = role;
            roleId = role.getId();
            role__resolvedKey = roleId;
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
    @Generated(hash = 1183361425)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserRoleDao() : null;
    }
}
