package cz.cvut.fit.shiftify.data;

import java.util.Date;
import java.util.Vector;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay {
    public WorkDay() { }
    public WorkDay(Date date, Vector<Shift> shifts) {
        setDate(date);
        setShifts(shifts);
    }

    protected Date date;
    protected Vector<Shift> shifts;

    // getters and setters
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Vector<Shift> getShifts() {
        return shifts;
    }
    public void setShifts(Vector<Shift> shifts) {
        this.shifts = shifts;
    }
}
