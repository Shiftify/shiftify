package cz.cvut.fit.shiftify.data;

import org.joda.time.LocalDate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay implements Comparator<WorkDay>, Comparable<WorkDay> {
    // Constructors

    public WorkDay() { }
    public WorkDay(LocalDate date) { // no work this day
        this(date, null);
    }
    public WorkDay(LocalDate date, List<Shift> shifts) {
        setDate(date);
        setShifts(shifts);
    }

    protected LocalDate date;
    protected List<Shift> shifts;

    // Getters and setters

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Shift> getShifts() {
        return shifts;
    }
    public void setShifts(List<Shift> shifts) {
        this.shifts = (shifts == null ? Collections.<Shift>emptyList() : shifts);
    }

    public boolean hasShifts() {
        return !shifts.isEmpty();
    }

    // Comparator methods

    @Override
    public int compareTo(WorkDay workDay) {
        return compare(this, workDay);
    }

    @Override
    public int compare(WorkDay workDay1, WorkDay workDay2) {
        if (workDay1.shifts.isEmpty())
            return workDay2.shifts.isEmpty() ? 0 : 1;
        if (workDay2.shifts.isEmpty())
            return -1;
        return  workDay1.shifts.get(0).compareTo(workDay2.shifts.get(0));
    }
}