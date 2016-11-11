package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

abstract class Shift {
    public Shift() { }
    public Shift(String name, Time from, Time duration) {
        this(name, from, duration, null);
    }
    public Shift(String name, Time from, Time duration, String description) {
        this.name = name;
        this.from = from;
        this.duration = duration;
        this.description = description;
    }

    protected String name;
    protected Time from;
    protected Time duration;
    protected String description;

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Time getFrom() {
        return from;
    }
    public void setFrom(Time from) {
        this.from = from;
    }
    public Time getDuration() {
        return duration;
    }
    public void setDuration(Time duration) {
        this.duration = duration;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean persistsIntoNextDay() throws Exception {
        if (from == null || duration == null)
            throw new Exception("Null attribute (From or Duration or both) in Shift object.");
        return new Time(from.getHours() + duration.getHours(),
                from.getMinutes() + duration.getMinutes(), 0).getHours() > 24;
    }
}
