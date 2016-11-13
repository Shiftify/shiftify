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
    public WorkDay(Date date, ScheduleShift scheduleShift, ExceptionShift exceptionShift) {
        setDate(date);
        setScheduleShift(scheduleShift);
        setExceptionShift(exceptionShift);
    }

    protected Date date;
    protected ScheduleShift scheduleShift;
    protected ExceptionShift exceptionShift;

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
    public ExceptionShift getExceptionShift() {
        return exceptionShift;
    }
    public void setExceptionShift(ExceptionShift exceptionShift) {
        this.exceptionShift = exceptionShift;
    }
}
