package cz.cvut.fit.shiftify.data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 11.11.2016.
 */

// dummy implementation at this point
public class ScheduleTypeManager {
    /**
     * Adds a scheduleType. This instance has a list of scheduleShifts.
     */
    public void add(ScheduleType scheduleType) throws Exception {
        scheduleType.setId(3);
    }

    /**
     * Edits scheduleType. This instance needs to have an id.
     * Beware of the list of scheduleShifts. U edit them here the same way as well.
     */
    public void edit(ScheduleType scheduleType) throws Exception {
    }

    /**
     * Deletes a scheduleType with an id equal to scheduleTypeId.
     * Deletes all its scheduleShifts automatically as well.
     */
    public void delete(long scheduleTypeId) throws Exception {
    }

    /**
     * Gets a scheduleType with an id equal to scheduleTypeId.
     * This instance has a list of its scheduleShifts.
     */
    public ScheduleType scheduleType(long scheduleTypeId) throws Exception {
        List<ScheduleShift> shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 2));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 1, 3));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 5));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 1, 6));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i-1).setId(i);
        ScheduleType scheduleType = new ScheduleType("Železárny", 7);
        scheduleType.setId(1);
        scheduleType.setShifts(shifts);
        return scheduleType;
    }

    /**
     * Gets a list of all scheduleTypes.
     */
    public List<ScheduleType> scheduleTypes() throws Exception {
        List<ScheduleType> scheduleTypes = new ArrayList<>();
        List<ScheduleShift> shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 2, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 1, 3));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 1, 5, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 1, 6));
        for (int i = 1; i <= shifts.size(); ++i) shifts.get(i-1).setId(i);
        ScheduleType scheduleType = new ScheduleType("Železárny", 7, "Slouží jako rozpis pro vrátnýho.");
        scheduleType.setId(1);
        scheduleType.setShifts(shifts);
        scheduleTypes.add(scheduleType); // adds first schedule type
        shifts = new ArrayList<>();
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 2, 1, "To se bude blbě vstávat."));
        shifts.add(new ScheduleShift("ranni", new Time(6, 0, 0), new Time(8, 0, 0), 2, 2));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 3));
        shifts.add(new ScheduleShift("odpoledni", new Time(14, 0, 0), new Time(8, 0, 0), 2, 4, "Já jsem poznámka."));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 2, 5));
        shifts.add(new ScheduleShift("nocni", new Time(22, 0, 0), new Time(8, 0, 0), 2, 6, "komentář"));
        for (int i = 5; i-4 <= shifts.size(); ++i) shifts.get(i-5).setId(i);
        scheduleType = new ScheduleType("Železárny_hasič", 8);
        scheduleType.setId(2);
        scheduleType.setShifts(shifts);
        scheduleTypes.add(scheduleType); // adds second schedule type
        return scheduleTypes;
    }
}