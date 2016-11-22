package cz.cvut.fit.shiftify.data;

import java.util.Date;
import java.util.Vector;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay {
    public WorkDay() { }
    public WorkDay(Date date) { // no work this day
        this(date, null, null);
    }
    public WorkDay(Date date, ScheduleShift scheduleShift) {
        this(date, scheduleShift, null);
    }
    public WorkDay(Date date, ScheduleShift scheduleShift, Vector<ExceptionShift> exceptionShifts) {
        setDate(date);
        setScheduleShift(scheduleShift);
        setExceptionShifts(exceptionShifts);
    }

    protected Date date;
    protected ScheduleShift scheduleShift;
    protected Vector<ExceptionShift> exceptionShifts;

    // getters and setters
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public ScheduleShift getScheduleShift() {
        return scheduleShift;
    }
    public void setScheduleShift(ScheduleShift scheduleShift) {
        this.scheduleShift = scheduleShift;
    }
    public Vector<ExceptionShift> getExceptionShifts() {
        return exceptionShifts;
    }
    public void setExceptionShifts(Vector<ExceptionShift> exceptionShifts) {
        this.exceptionShifts = exceptionShifts;
    }

    public boolean hasShifts() {
         return hasScheduleShift() || hasExceptionShifts();
    }
    public boolean hasScheduleShift() {
        return scheduleShift != null;
    }
    public boolean hasExceptionShifts() {
        return exceptionShifts != null && !exceptionShifts.isEmpty();
    }

    public boolean persistsIntoNextDay() throws Exception {
        if (hasExceptionShifts()) {
            for (ExceptionShift ex : exceptionShifts)
                if (ex.persistsIntoNextDay())
                    return true;
            return false;
        }
        if (hasScheduleShift())
            return scheduleShift.persistsIntoNextDay();
        return false;
    }
    public Vector<Shift> shiftsPersistingIntoNextDay() throws Exception {
        Vector<Shift> shifts = new Vector<Shift>();
        if (hasExceptionShifts()) {
            for (ExceptionShift ex : exceptionShifts)
                if (ex.persistsIntoNextDay())
                    shifts.add(ex);
            return shifts;
        }
        if (hasScheduleShift() && scheduleShift.persistsIntoNextDay())
            shifts.add(scheduleShift);
        return shifts;
    }
}
