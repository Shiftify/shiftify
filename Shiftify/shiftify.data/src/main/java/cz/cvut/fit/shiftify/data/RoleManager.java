package cz.cvut.fit.shiftify.data;

import java.util.Vector;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class RoleManager {
    public void add(Role role) throws Exception {
    }
    public void edit(int roleId, Role role) throws Exception {
    }
    public void delete(int roleId) throws Exception {
    }

    public Role role(int roleId) throws Exception {
        return new Role("admin", null);
    }
    public Vector<Role> roles() {
        Vector<Role> roles = new Vector<Role>();
        roles.add(new Role("admin", null));
        roles.add(new Role("leader", "A member of a team, that takes care of the others."));
        return roles;
    }
}