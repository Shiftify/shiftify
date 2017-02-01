package cz.cvut.fit.shiftify.data.managers;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.Role;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class RoleManager {
    /**
     * Adds role.
     */
    public void add(Role role) throws Exception {
        role.setId(4L);
    }

    /**
     * Edits role. This instance needs to have an id.
     */
    public void edit(Role role) throws Exception {
    }

    /**
     * Deletes role with an id equal to roleId.
     */
    public void delete(int roleId) throws Exception {
    }

    /**
     * Gets a role with an id equal to roleId.
     */
    public Role role(int roleId) throws Exception {
        return new Role("admin", null);
    }

    /**
     * Gets a list of all roles.
     */
    public List<Role> roles() throws Exception {
        List<Role> roles = new ArrayList<>();
        Role role = new Role("admin", null);
        role.setId(1L);
        roles.add(role);
        role = new Role("leader", "A member of a team, that takes care of the others.");
        role.setId(2L);
        roles.add(role);
        return roles;
    }
}