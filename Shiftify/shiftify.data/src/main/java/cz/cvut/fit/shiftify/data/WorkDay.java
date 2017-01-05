package cz.cvut.fit.shiftify.data;

import java.util.Date;
import java.util.List;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay {
    public WorkDay() { }
    public WorkDay(Date date) { // no work this day
        this(date, null);
    }
    public WorkDay(Date date, List<Shift> shifts) {
        setDate(date);
        setShifts(shifts);
    }

    protected Date date;
    protected List<Shift> shifts;

    // getters and setters
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
}
