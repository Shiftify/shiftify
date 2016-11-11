package cz.cvut.fit.shiftify.data;

import java.util.Date;

/**
 * Created by lukas on 11.11.2016.
 */

public class Schedule {
    public Schedule() { }
    public Schedule(Integer scheduleTypeId, Date from, Date to, Integer startingDayOfScheduleCycle) {
        this.scheduleTypeId = scheduleTypeId;
        this.from = from;
        this.to = to;
        this.startingDayOfScheduleCycle = startingDayOfScheduleCycle;
    }

    protected Integer id;
    protected Integer scheduleTypeId;
    protected Date from;
    protected Date to;
    protected Integer startingDayOfScheduleCycle;

    // getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getScheduleTypeId() {
        return this.scheduleTypeId;
    }
    public void setScheduleTypeId(Integer scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }
    public Date getFrom() {
        return this.from;
    }
    public void setFrom(Date from) {
        this.from = from;
    }
    public Date getTo() {
        return this.to;
    }
    public void setTo(Date to) {
        this.to = to;
    }
    public Integer getStartingDayOfScheduleCycle() {
        return this.startingDayOfScheduleCycle;
    }
    public void setStartingDayOfScheduleCycle(Integer startingDayOfScheduleCycle) {
        this.startingDayOfScheduleCycle = startingDayOfScheduleCycle;
    }
}
