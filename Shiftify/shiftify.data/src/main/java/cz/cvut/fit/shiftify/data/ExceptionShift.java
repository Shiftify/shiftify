package cz.cvut.fit.shiftify.data;

import java.sql.Time;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionShift extends Shift {
    public ExceptionShift() { }
    public ExceptionShift(String name, Time from, Time duration, Integer exceptionInScheduleId) {
        this(name, from, duration, exceptionInScheduleId, null);
    }
    public ExceptionShift(String name, Time from, Time duration, Integer exceptionInScheduleId, String description) {
        super(name, from, duration, description);
        ExceptionInScheduleId = exceptionInScheduleId;
    }

    public Integer Id;
    public Integer ExceptionInScheduleId;
}