package cz.cvut.fit.shiftify.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay implements Comparator<WorkDay>, Comparable<WorkDay> {
    // Constructors
    public WorkDay() { this(null, null); }
    public WorkDay(GregorianCalendar date) { // no work this day
        this(date, null);
    }
    public WorkDay(GregorianCalendar date, List<Shift> shifts) {
        setDate(date);
        setShifts(shifts);
    }

    protected GregorianCalendar date;
    protected List<Shift> shifts;

    // Getters and setters
    public GregorianCalendar getDate() {
        return date;
    }
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
    public List<Shift> getShifts() {
        return shifts;
    }
    public void setShifts(List<Shift> shifts) {
        this.shifts = (shifts == null ? new ArrayList<Shift>() : shifts);
    }

    public boolean hasShifts() {
         return shifts.isEmpty();
    }

    // Comparator methods
    @Override
    public int compareTo(WorkDay workDay) {
        return compare(this, workDay);
    }
    @Override
    public int compare(WorkDay workDay1, WorkDay workDay2) {
        if (workDay1.shifts.size() == 0) {
            if (workDay2.shifts.size() == 0)
                return 0;
            return 1;
        }
        if (workDay2.shifts.size() == 0)
            return -1;
        long diff = workDay1.shifts.get(0).getFrom().getTimeInMillis() - workDay2.shifts.get(0).getFrom().getTimeInMillis();
        return (diff == 0 ? 0 : (diff > 0 ? 1 : -1));
    }
}
