package cz.cvut.fit.shiftify.data.managers;

import org.greenrobot.greendao.DaoException;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.App;
import cz.cvut.fit.shiftify.data.models.DaoSession;
import cz.cvut.fit.shiftify.data.models.Role;
import cz.cvut.fit.shiftify.data.models.RoleDao;
import cz.cvut.fit.shiftify.data.models.ScheduleShiftDao;
import cz.cvut.fit.shiftify.data.models.ScheduleTypeDao;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class RoleManager {

    private RoleDao roleDao;

    public RoleManager() {
        DaoSession daoSession = App.getNewDaoSession();
        daoSession.clear();
        roleDao = daoSession.getRoleDao();
    }

    /**
     * Adds role.
     */
    public void add(Role role) throws Exception {
        roleDao.insert(role);
    }

    /**
     * Edits role. This instance needs to have an id.
     */
    public void edit(Role role) throws Exception {
        if (role.getId() != null)
            throw new DaoException("Trying to update a role that has no id.");
        roleDao.update(role);
    }

    /**
     * Deletes role with an id equal to roleId.
     */
    public void delete(long roleId) throws Exception {
        roleDao.delete(role(roleId));
    }

    /**
     * Gets a role with an id equal to roleId.
     */
    public Role role(long roleId) throws Exception {
        return roleDao.load(roleId);
    }

    /**
     * Gets a list of all roles.
     */
    public List<Role> roles() throws Exception {
        return roleDao.queryBuilder().orderAsc(RoleDao.Properties.Name).list();
    }
}