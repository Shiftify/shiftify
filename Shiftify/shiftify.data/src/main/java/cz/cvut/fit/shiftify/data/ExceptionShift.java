package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionShift extends Shift {
    public ExceptionShift() { }
    public ExceptionShift(Time from, Time duration, Integer exceptionInScheduleId) {
        this(from, duration, exceptionInScheduleId, null);
    }
    public ExceptionShift(Time from, Time duration, Integer exceptionInScheduleId, String description) {
        super(from, duration, description);
        setExceptionInScheduleId(exceptionInScheduleId);
    }

    protected Integer id;
    protected Integer exceptionInScheduleId;

    // getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getExceptionInScheduleId() {
        return exceptionInScheduleId;
    }
    public void setExceptionInScheduleId(Integer exceptionInScheduleId) {
        this.exceptionInScheduleId = exceptionInScheduleId;
    }
}