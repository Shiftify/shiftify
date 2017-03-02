package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "Role",
        generateConstructors = false,
        generateGettersSetters = false,
        indexes = { @Index(name="Unique_Role_Name", unique = true, value = "name ASC") })
public class Role {
    // Columns
    @Id(autoincrement = true)
    @Property(nameInDb = "Id")
    protected Long id;
    @NotNull
    @Property(nameInDb = "Name")
    protected String name;
    @Property(nameInDb = "Description")
    protected String description;

    // Relationships
    @ToMany
    @JoinEntity(entity = UserRole.class, sourceProperty = "roleId", targetProperty = "userId")
    @OrderBy(value = "surname")
    private List<User> users;

    // Constructors
    public Role() {
        this(null, null, null);
    }
    public Role(@NotNull String name) {
        this(null, name, null);
    }
    public Role(@NotNull String name, String description) {
        this(null, name, description);
    }
    public Role(Long id, @NotNull String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(@NotNull String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // GreenDAO generated attributes
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1785861362)
    private transient RoleDao myDao;

    // GreenDAO generated methods
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 37859473)
    public List<User> getUsers() {
        if (users == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> usersNew = targetDao._queryRole_Users(id);
            synchronized (this) {
                if (users == null) {
                    users = usersNew;
                }
            }
        }
        return users;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1027274768)
    public synchronized void resetUsers() {
        users = null;
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
    @Generated(hash = 957214601)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRoleDao() : null;
    }
}
