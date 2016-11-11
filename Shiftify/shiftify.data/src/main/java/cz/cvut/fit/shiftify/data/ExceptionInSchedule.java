package cz.cvut.fit.shiftify.data;

import java.sql.Date;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionInSchedule {
    public ExceptionInSchedule() { }
    public ExceptionInSchedule(Date date, Integer scheduleId) {
        this(date, scheduleId, null);
    }
    public ExceptionInSchedule(Date date, Integer scheduleId, String description) {
        Date = date;
        ScheduleId = scheduleId;
        Description = description;
    }

    public Integer Id;
    public Integer ScheduleId;
    public Date Date;
    public String Description;
}
