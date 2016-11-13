package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

abstract public class Shift {
    public Shift() { }
    public Shift(Time from, Time duration) {
        this(from, duration, null);
    }
    public Shift(Time from, Time duration, String description) {
        setFrom(from);
        setDuration(duration);
        setDescription(description);
    }

    protected Time from;
    protected Time duration;
    protected String description;

    // getters and setters
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
