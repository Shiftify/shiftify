package cz.cvut.fit.shiftify.data;

import java.util.Vector;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class RoleManager {
    public void add(Role role) throws Exception {
        role.setId(4);
    }
    public void edit(int roleId, Role role) throws Exception {
        role.setId(roleId);
    }
    public void delete(int roleId) throws Exception {
    }
    public Role role(int roleId) throws Exception {
        return new Role("admin", null);
    }
    public Vector<Role> roles() throws Exception {
        Vector<Role> roles = new Vector<Role>();
        Role role = new Role("admin", null);
        role.setId(1);
        roles.add(role);
        role = new Role("leader", "A member of a team, that takes care of the others.");
        role.setId(2);
        roles.add(role);
        return roles;
    }
}