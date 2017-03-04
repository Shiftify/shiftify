package cz.cvut.fit.shiftify.data.managers;

import org.greenrobot.greendao.DaoException;

import java.util.List;

import cz.cvut.fit.shiftify.data.App;
import cz.cvut.fit.shiftify.data.models.DaoSession;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleShiftDao;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.models.ScheduleTypeDao;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class ScheduleTypeManager {

    private ScheduleTypeDao scheduleTypeDao;
    private ScheduleShiftDao scheduleShiftDao;

    public ScheduleTypeManager() {
        DaoSession daoSession = App.getNewDaoSession();
        daoSession.clear();
        scheduleTypeDao = daoSession.getScheduleTypeDao();
        scheduleShiftDao = daoSession.getScheduleShiftDao();
    }

    // ScheduleTypes
    /**
     * Adds a scheduleType. This instance has a list of scheduleShifts.
     */
    public void add(ScheduleType scheduleType) throws Exception {
        scheduleTypeDao.insert(scheduleType);
        for (ScheduleShift shift : scheduleType.getShifts())
            addShift(scheduleType.getId(), shift);
    }

    /**
     * Edits scheduleType. This instance needs to have an id.
     * Beware of the list of scheduleShifts. U edit them here the same way as well.
     */
    public void edit(ScheduleType scheduleType) throws Exception {
        if (scheduleType.getId() != null)
            throw new DaoException("Trying to update a scheduleType that has no id.");
        ScheduleType tmp = scheduleTypeDao.load(scheduleType.getId());
        boolean found;
        for (ScheduleShift sh : tmp.getShifts()) {
            found = false;
            for (ScheduleShift shift : scheduleType.getShifts())
                if (sh.getId() == shift.getId()) {
                    found = true;
                    break;
                }
            if (!found)
                scheduleShiftDao.delete(sh);
        }
        scheduleTypeDao.save(scheduleType);
        for (ScheduleShift shift : scheduleType.getShifts())
            if (shift.getId() == null)
                addShift(scheduleType.getId(), shift);
            else
                editShift(shift);
    }

    /**
     * Deletes a scheduleType with an id equal to scheduleTypeId.
     * Deletes all its scheduleShifts automatically as well.
     */
    public void delete(long scheduleTypeId) throws Exception {
        deleteShifts(scheduleTypeId);
        scheduleTypeDao.deleteByKey(scheduleTypeId);
    }

    /**
     * Deletes all scheduleTypes in the database.
     */
    public void deleteAll() throws Exception {
        scheduleTypeDao.deleteInTx(scheduleTypeDao.loadAll());
    }

    /**
     * Gets a scheduleType with an id equal to scheduleTypeId.
     * This instance has a list of its scheduleShifts.
     */
    public ScheduleType scheduleType(long scheduleTypeId) throws Exception {
        return scheduleTypeDao.load(scheduleTypeId);
    }

    /**
     * Gets a list of all scheduleTypesAll.
     */
    public List<ScheduleType> scheduleTypes() throws Exception {
        return scheduleTypeDao.queryBuilder().orderAsc(ScheduleTypeDao.Properties.Name).list();
    }

    // ScheduleShifts
    private void addShift(long scheduleTypeId, ScheduleShift scheduleShift) throws Exception {
        scheduleShift.setScheduleTypeId(scheduleTypeId);
        scheduleShift.setId(scheduleShiftDao.insert(scheduleShift));
    }

    private void editShift(ScheduleShift scheduleShift) throws Exception {
        if (scheduleShift.getId() != null)
            throw new DaoException("Trying to update a scheduleShift that has no id.");
        scheduleShiftDao.save(scheduleShift);
    }

    private void deleteShift(long scheduleShiftId) throws Exception {
        scheduleShiftDao.deleteByKey(scheduleShiftId);
    }

    private void deleteShifts(long scheduleTypeId) throws Exception {
        scheduleShiftDao.deleteInTx(scheduleTypeDao.load(scheduleTypeId).getShifts());
    }

    private ScheduleShift shift(long scheduleShiftId) throws Exception {
        return scheduleShiftDao.load(scheduleShiftId);

    }

    private List<ScheduleShift> shifts(long scheduleTypeId) throws Exception {
        return scheduleTypeDao.load(scheduleTypeId).getShifts();
    }

    private List<ScheduleShift> shifts() throws Exception {
        return scheduleShiftDao.queryBuilder().orderAsc(ScheduleShiftDao.Properties.From).list();
    }
}