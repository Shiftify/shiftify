package cz.cvut.fit.shiftify.data;

import java.util.Date;

/**
 * Created by lukas on 11.11.2016.
 */

public class Schedule {
    public Schedule() { }
    public Schedule(Integer userId, Integer scheduleTypeId, Date from, Date to, Integer startingDayOfScheduleCycle) {
        setUserId(userId);
        setScheduleTypeId(scheduleTypeId);
        setFrom(from);
        setTo(to);
        setStartingDayOfScheduleCycle(startingDayOfScheduleCycle);
    }

    protected Integer id;
    protected Integer userId;
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
    public Integer getUserId() {
        return this.userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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
