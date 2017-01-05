package cz.cvut.fit.shiftify.data;

import java.util.List;

/**
 * Created by lukas on 11.11.2016.
 */

public class ScheduleType {

    protected Long id;
    protected String name;
    protected Integer daysOfScheduleCycle;
    protected String description;

    public ScheduleType() { }
    public ScheduleType(String name, Integer daysOfScheduleCycle) {
        this(name, daysOfScheduleCycle, null);
    }
    public ScheduleType(String name, Integer daysOfScheduleCycle, String description) {
        setName(name);
        setDaysOfScheduleCycle(daysOfScheduleCycle);
        setDescription(description);
    }

    protected List<ScheduleShift> shifts;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDaysOfScheduleCycle() {
        return this.daysOfScheduleCycle;
    }
    public void setDaysOfScheduleCycle(Integer daysOfScheduleCycle) {
        this.daysOfScheduleCycle = daysOfScheduleCycle;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // dummy implementation at this point
    public List<ScheduleShift> getShifts() {
        return shifts;
    }
    public void setShifts(List<ScheduleShift> shifts) {
        this.shifts = shifts;
    }
}
