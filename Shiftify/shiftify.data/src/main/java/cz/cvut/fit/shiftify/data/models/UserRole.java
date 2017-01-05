package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(generateConstructors = false, generateGettersSetters = false)
public class UserRole {

    @Id(autoincrement = true)
    protected Long id;
    protected Long userId;
    protected Long roleId;

    public UserRole() { }
    public UserRole(Integer userId, Integer roleId) {
        setUserId(userId);
        setRoleId(roleId);
    }

    public UserRole(Long id, Long userId, Long roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
