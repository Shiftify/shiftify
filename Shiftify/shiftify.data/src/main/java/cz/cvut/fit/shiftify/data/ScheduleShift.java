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
        super(from, duration, description);
        setName(name);
        setScheduleTypeId(scheduleTypeId);
        setDayOfScheduleCycle(dayOfScheduleCycle);
    }

    protected Integer id;
    protected Integer scheduleTypeId;
    protected String name;
    protected Integer dayOfScheduleCycle;

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getScheduleTypeId() {
        return scheduleTypeId;
    }
    public void setScheduleTypeId(Integer scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }
    public Integer getDayOfScheduleCycle() {
        return dayOfScheduleCycle;
    }
    public void setDayOfScheduleCycle(Integer dayOfScheduleCycle) {
        this.dayOfScheduleCycle = dayOfScheduleCycle;
    }
}
