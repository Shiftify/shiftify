package cz.cvut.fit.shiftify.data;

import java.util.Date;
import java.util.List;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionInSchedule {

    protected Long id;
    protected Long scheduleId;
    protected Date date;
    protected String description;

    public ExceptionInSchedule() {
    }

    public ExceptionInSchedule(Date date, Long scheduleId) {
        this(date, scheduleId, null);
    }

    public ExceptionInSchedule(Date date, Long scheduleId, String description) {
        setDate(date);
        setScheduleId(scheduleId);
        setDescription(description);
    }

    protected List<ExceptionShift> shifts;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // dummy implementation at this point
    public List<ExceptionShift> getShifts() {
        return shifts;
    }

    public void setShifts(List<ExceptionShift> shifts) {
        this.shifts = shifts;
    }
}
