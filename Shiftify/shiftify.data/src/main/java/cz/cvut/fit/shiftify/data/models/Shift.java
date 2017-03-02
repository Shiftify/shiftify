package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import java.util.Comparator;

import org.joda.time.Period;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(createInDb = false,
        generateGettersSetters = false,
        generateConstructors = false)
abstract public class Shift implements Comparator<Shift>, Comparable<Shift> {
    // Constructors

    public Shift() { }
    public Shift(@NotNull LocalTime from, @NotNull Period duration) {
        this(null, from, duration, null);
    }
    public Shift(@NotNull LocalTime from, @NotNull Period duration, String description) {
        this(null, from, duration, description);
    }
    public Shift(Long id, @NotNull LocalTime from, @NotNull Period duration, String description) {
        setId(id);
        setFrom(from);
        setDuration(duration);
        setDescription(description);
    }

    public abstract Long getId();
    public abstract void setId(Long id);

    public abstract LocalTime getFrom();
    public abstract void setFrom(LocalTime from);

    public abstract Period getDuration();
    public abstract void setDuration(Period duration);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    // Methods

    abstract public String getName();

    public LocalTime getTo() {
        return getFrom().plus(getDuration());
    }

    public boolean persistsIntoNextDay() {
        if (getFrom() == null || getFrom() == null)
            return false;

        return getFrom().getMillisOfDay() + getDuration().toStandardDuration().getMillis() > new Period(24, 0, 0, 0).toStandardDuration().getMillis();
    }

    @Override
    public int compareTo(Shift shift) {
        return compare(this, shift);
    }
    @Override
    public int compare(Shift s1, Shift s2) {
        return s1.getFrom().compareTo(s2.getFrom());
    }
}
