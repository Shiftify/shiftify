package cz.cvut.fit.shiftify.data;

        import java.sql.Time;
        import java.sql.Date;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.GregorianCalendar;
        import java.util.Locale;
        import java.util.TimeZone;

/**
 * Created by lukas on 11.11.2016.
 */

abstract public class Shift {

    protected Time from;
    protected Time duration;
    protected String description;

    public Shift() { }
    public Shift(Time from, Time duration) {
        this(from, duration, null);
    }
    public Shift(Time from, Time duration, String description) {
        setFrom(from);
        setDuration(duration);
        setDescription(description);
    }

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

    public String getName() {
        return null;
    }
    public Boolean getIsWorking() {
        return true;
    }
    public boolean persistsIntoNextDay() throws Exception {
        if (from == null || duration == null)
            throw new Exception("Null attribute (From or Duration or both) in Shift object.");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int fromDays = Date.valueOf(sdf.format(from)).getDate() - 1;
        int durationDays = Date.valueOf(sdf.format(duration)).getDate() - 1;
        Time t = new Time((fromDays + durationDays)*24 + from.getHours() + duration.getHours(),
                from.getMinutes() + duration.getMinutes(), 0);
        return t.after(new Time(24, 0, 0));
    }
}
