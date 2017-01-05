package cz.cvut.fit.shiftify.data;

/**
 * Created by lukas on 11.11.2016.
 */

public class UserRole {

    protected Long id;
    protected Long userId;
    protected Long roleId;

    public UserRole() { }
    public UserRole(Integer userId, Integer roleId) {
        setUserId(userId);
        setRoleId(roleId);
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
}
