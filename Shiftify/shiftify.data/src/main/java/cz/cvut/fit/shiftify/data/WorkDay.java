package cz.cvut.fit.shiftify.data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay implements Comparator<WorkDay>, Comparable<WorkDay> {
    // Constructors
    public WorkDay() { this(null, null); }
    public WorkDay(Date date) { // no work this day
        this(date, null);
    }
    public WorkDay(Date date, List<Shift> shifts) {
        setDate(date);
        setShifts(shifts);
    }

    protected Date date;
    protected List<Shift> shifts;

    // Getters and setters
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public List<Shift> getShifts() {
        return shifts;
    }
    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public boolean hasShifts() {
         return shifts == null || shifts.isEmpty();
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
