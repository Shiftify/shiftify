package cz.cvut.fit.shiftify.data;

import java.sql.Date;
import java.util.Vector;

/**
 * Created by lukas on 11.11.2016.
 */

public class ExceptionInSchedule {
    public ExceptionInSchedule() { }
    public ExceptionInSchedule(Date date, Integer scheduleId) {
        this(date, scheduleId, null);
    }
    public ExceptionInSchedule(Date date, Integer scheduleId, String description) {
        setDate(date);
        setScheduleId(scheduleId);
        setDescription(description);
    }

    protected Integer id;
    protected Integer scheduleId;
    protected Date date;
    protected String description;

    protected Vector<ExceptionShift> shifts;

    // getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(Integer scheduleId) {
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
    public Vector<ExceptionShift> getShifts() {
        return shifts;
    }
    public void setShifts(Vector<ExceptionShift> shifts) {
        this.shifts = shifts;
    }
}
