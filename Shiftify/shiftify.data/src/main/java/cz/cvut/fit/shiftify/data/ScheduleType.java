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
        Name = name;
        DaysOfScheduleCycle = daysOfScheduleCycle;
        Description = description;
    }

    public Integer Id;
    public String Name;
    public Integer DaysOfScheduleCycle;
    public String Description;
}
