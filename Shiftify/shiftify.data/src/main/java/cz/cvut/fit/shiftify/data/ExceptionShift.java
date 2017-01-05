package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionShift extends Shift {

    protected Long id;
    protected Long exceptionInScheduleId;
    protected Boolean isWorking;

    public ExceptionShift() { }
    public ExceptionShift(Time from, Time duration, Long exceptionInScheduleId, Boolean isWorking) {
        this(from, duration, exceptionInScheduleId, isWorking, null);
    }
    public ExceptionShift(Time from, Time duration, Long exceptionInScheduleId, Boolean isWorking, String description) {
        super(from, duration, description);
        setExceptionInScheduleId(exceptionInScheduleId);
        setIsWorking(isWorking);
    }

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Long getExceptionInScheduleId() {
        return exceptionInScheduleId;
    }
    public void setExceptionInScheduleId(long exceptionInScheduleId) {
        this.exceptionInScheduleId = exceptionInScheduleId;
    }
    public Boolean getIsWorking() {
        return isWorking;
    }
    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
    }
}