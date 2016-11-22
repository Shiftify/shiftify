package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionShift extends Shift {
    public ExceptionShift() { }
    public ExceptionShift(Time from, Time duration, Integer exceptionInScheduleId, Boolean isWorking) {
        this(from, duration, exceptionInScheduleId, isWorking, null);
    }
    public ExceptionShift(Time from, Time duration, Integer exceptionInScheduleId, Boolean isWorking, String description) {
        super(from, duration, description);
        setExceptionInScheduleId(exceptionInScheduleId);
        setIsWorking(isWorking);
    }

    protected Integer id;
    protected Integer exceptionInScheduleId;
    protected Boolean isWorking;

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
    public Boolean getIsWorking() {
        return isWorking;
    }
    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
    }
}