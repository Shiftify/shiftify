package cz.cvut.fit.shiftify.data.models;

        import org.greenrobot.greendao.annotation.Convert;
        import org.greenrobot.greendao.annotation.Entity;
        import org.greenrobot.greendao.annotation.Id;
        import org.greenrobot.greendao.annotation.NotNull;
        import org.greenrobot.greendao.annotation.Property;

        import java.sql.Time;
        import java.sql.Date;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.GregorianCalendar;
        import java.util.Locale;
        import java.util.TimeZone;

        import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_Time_Converter;
        import cz.cvut.fit.shiftify.data.Utilities;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(createInDb = false,
        generateGettersSetters = false,
        generateConstructors = false)
abstract public class Shift {
    // Constructors
    public Shift() {
        this(null, null, null, null);
    }
    public Shift(@NotNull GregorianCalendar from, @NotNull GregorianCalendar duration) {
        this(null, from, duration, null);
    }
    public Shift(@NotNull GregorianCalendar from, @NotNull GregorianCalendar duration, String description) {
        this(null, from, duration, description);
    }
    public Shift(Long id, @NotNull GregorianCalendar from, @NotNull GregorianCalendar duration, String description) {
        setId(id);
        setFrom(from);
        setDuration(duration);
        setDescription(description);
    }

    // Getters and setters
    public Long getId() { return null; }
    public void setId(Long id) { }
    public GregorianCalendar getFrom() {
        return null;
    }
    public void setFrom(GregorianCalendar from) { }
    public GregorianCalendar getDuration() {
        return null;
    }
    public void setDuration(GregorianCalendar duration) { }
    public String getDescription() {
        return null;
    }
    public void setDescription(String description) { }
    public String getName() {
        return null;
    }
    public Boolean getIsWorking() {
        return true;
    }

    // Methods
    public GregorianCalendar getTo() {
        return Utilities.GregCalTimeAddition(getFrom(), getDuration());
    }
    public String getFromToString() {
        return getFrom().get(Calendar.HOUR_OF_DAY) + "." + getFrom().get(Calendar.MINUTE) + " - "
                + getTo().get(Calendar.HOUR_OF_DAY) + "." + getTo().get(Calendar.MINUTE);
    }
    public boolean persistsIntoNextDay() throws Exception {
        if (getFrom() == null || getDuration() == null)
            throw new Exception("Null attribute (From or Duration or both) in Shift object.");
        //TODO: implement addition and subtraction of GregorianCalendar in Utilities
        //      so that the code is easier to read
        return getFrom().getTimeInMillis() + getDuration().getTimeInMillis() > 24*60*60*1000;
    }
    public boolean isExceptionShift() { return false; }
}
