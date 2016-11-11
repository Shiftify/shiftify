package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

public class ScheduleShift extends Shift {
    public ScheduleShift() { }
    public ScheduleShift(String name, Time from, Time duration, Integer scheduleTypeId, Integer dayOfScheduleCycle) {
        this(name, from, duration, scheduleTypeId, dayOfScheduleCycle, null);
    }
    public ScheduleShift(String name, Time from, Time duration, Integer scheduleTypeId, Integer dayOfScheduleCycle, String description) {
        super(name, from, duration, description);
        ScheduleTypeId = scheduleTypeId;
        DayOfScheduleCycle = dayOfScheduleCycle;
    }

    public Integer Id;
    public Integer ScheduleTypeId;
    public Integer DayOfScheduleCycle;
}
