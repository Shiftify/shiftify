package cz.cvut.fit.shiftify.data;

/**
 * Created by lukas on 11.11.2016.
 */

public class ScheduleType {
    public ScheduleType() { }
    public ScheduleType(String name, Integer daysOfScheduleCycle) {
        this(name, daysOfScheduleCycle, null);
    }
    public ScheduleType(String name, Integer daysOfScheduleCycle, String description) {
        this.name = name;
        this.daysOfScheduleCycle = daysOfScheduleCycle;
        this.description = description;
    }

    protected Integer id;
    protected String name;
    protected Integer daysOfScheduleCycle;
    protected String description;

    // getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
}
