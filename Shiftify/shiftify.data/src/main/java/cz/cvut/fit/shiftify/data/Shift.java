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
        Name = name;
        From = from;
        Duration = duration;
        Description = description;
    }

    public String Name;
    public Time From;
    public Time Duration;
    public String Description;

    public boolean PersistsIntoNextDay() throws Exception {
        if (From == null || Duration == null)
            throw new Exception("Null attribute (From or Duration or both) in Shift object.");
        return new Time(From.getHours() + Duration.getHours(),
                From.getMinutes() + Duration.getMinutes(), 0).getHours() > 24;
    }
}
