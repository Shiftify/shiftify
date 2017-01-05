package cz.cvut.fit.shiftify.data.managers;

import java.sql.Time;
import java.util.ArrayList;
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
        scheduleTypeDao = daoSession.getScheduleTypeDao();
        scheduleShiftDao = daoSession.getScheduleShiftDao();
    }

    /**
     * Adds a scheduleType. This instance has a list of scheduleShifts.
     */
    public void add(ScheduleType scheduleType) throws Exception {
        scheduleTypeDao.insert(scheduleType);
    }

    /**
     * Edits scheduleType. This instance needs to have an id.
     * Beware of the list of scheduleShifts. U edit them here the same way as well.
     */
    public void edit(ScheduleType scheduleType) throws Exception {
        scheduleTypeDao.save(scheduleType);
    }

    /**
     * Deletes a scheduleType with an id equal to scheduleTypeId.
     * Deletes all its scheduleShifts automatically as well.
     */
    public void delete(long scheduleTypeId) throws Exception {
        deleteShift(scheduleTypeId);
        scheduleTypeDao.deleteByKey(scheduleTypeId);
    }

    public void deleteAll() throws Exception {
        for (ScheduleType type : scheduleTypeDao.loadAll()) {
            delete(type.getId());
        }
    }

    /**
     * Gets a scheduleType with an id equal to scheduleTypeId.
     * This instance has a list of its scheduleShifts.
     */
    public ScheduleType scheduleType(long scheduleTypeId){
        return scheduleTypeDao.load(scheduleTypeId);
    }

    /**
     * Gets a list of all scheduleTypesAll.
     */
    public List<ScheduleType> scheduleTypesAll() {
        return scheduleTypeDao.loadAll();
    }

    public void addShift(long scheduleTypeId, ScheduleShift scheduleShift) throws Exception {
        scheduleShift.setScheduleTypeId(scheduleTypeId);
        scheduleShiftDao.insert(scheduleShift);
    }

    public void editShift(long scheduleTypeId, ScheduleShift scheduleShift) throws Exception {
        scheduleShift.setScheduleTypeId(scheduleTypeId);
        scheduleShiftDao.save(scheduleShift);
    }

    public void deleteShift(Long scheduleShiftId) throws Exception {
        scheduleShiftDao.deleteByKey(scheduleShiftId);
    }

    public ScheduleShift shift(long scheduleShiftId) throws Exception {
        return scheduleShiftDao.load(scheduleShiftId);

    }

    public List<ScheduleShift> shifts(long scheduleTypeId) {
        return scheduleTypeDao.load(scheduleTypeId).getShifts();
    }

    public List<ScheduleShift> shiftsAll() throws Exception {
        return scheduleShiftDao.loadAll();
    }

    public void deleteShifts(long scheduleTypeId) throws Exception {
        for (ScheduleShift shift : scheduleTypeDao.load(scheduleTypeId).getShifts()) {
            deleteShift(shift.getId());
        }
    }
}