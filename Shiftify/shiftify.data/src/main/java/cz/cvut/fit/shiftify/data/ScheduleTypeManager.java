package cz.cvut.fit.shiftify.data;

import java.util.Vector;
import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class ScheduleTypeManager {
    public void add(ScheduleType scheduleType) throws Exception {
        scheduleType.setId(3);
    }
    public void edit(int scheduleTypeId, ScheduleType scheduleType) throws Exception {
        scheduleType.setId(scheduleTypeId);
    }
    public void delete(int scheduleTypeId) throws Exception {
    }
    public ScheduleType scheduleType(int scheduleTypeId) throws Exception {
        Vector<ScheduleShift> shifts = new Vector<ScheduleShift> ();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 2));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 1, 3));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 5));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 1, 6));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i).setId(i);
        ScheduleType scheduleType = new ScheduleType("Železárny", 7);
        scheduleType.setId(1);
        scheduleType.setShifts(shifts);
        return scheduleType;
    }
    public Vector<ScheduleType> scheduleTypes() throws Exception {
        Vector<ScheduleType> scheduleTypes = new Vector<ScheduleType>();
        Vector<ScheduleShift> shifts = new Vector<ScheduleShift> ();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 2, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 1, 3));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 5, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 1, 6));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i).setId(i);
        ScheduleType scheduleType = new ScheduleType("Železárny", 7, "Slouží jako rozpis pro vrátnýho.");
        scheduleType.setId(1);
        scheduleType.setShifts(shifts);
        scheduleTypes.add(scheduleType); // adds first schedule type
        shifts = new Vector<ScheduleShift> ();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 2, 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 2, 2));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 3));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 2, 5));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 2, 6, "komentář"));
        for (int i = 5; i-4 <= shifts.size(); ++i) shifts.get(i).setId(i);
        scheduleType = new ScheduleType("Železárny_hasič", 8);
        scheduleType.setId(2);
        scheduleType.setShifts(shifts);
        scheduleTypes.add(scheduleType); // adds second schedule type
        return scheduleTypes;
    }

    public void addShift(int scheduleTypeId, ScheduleShift shift) throws Exception {
        shift.setId(11);
    }
    public void editShift(int shiftId, ScheduleShift shift) throws Exception {
        shift.setId(shiftId);
    }
    public void deleteShift(int shiftId) throws Exception {
    }
    public ScheduleShift shift(int shiftId) throws Exception {
        ScheduleShift shift = new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 3);
        shift.setId(shiftId);
        return shift;
    }
    public Vector<ScheduleShift> shifts(int scheduleTypeId) throws Exception {
        Vector<ScheduleShift> shifts = new Vector<ScheduleShift> ();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 2, 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 2, 2));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 3));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 2, 5));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 2, 6, "komentář"));
        for (int i = 5; i-4 <= shifts.size(); ++i) shifts.get(i).setId(i);
        return shifts;
    }
}