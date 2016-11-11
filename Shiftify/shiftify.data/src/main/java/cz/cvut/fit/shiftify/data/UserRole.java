package cz.cvut.fit.shiftify.data;

/**
 * Created by lukas on 11.11.2016.
 */

public class UserRole {
    public UserRole() { }
    public UserRole(Integer userId, Integer roleId) {
        UserId = userId;
        RoleId = roleId;
    }

    public Integer Id;
    public Integer UserId;
    public Integer RoleId;
}
