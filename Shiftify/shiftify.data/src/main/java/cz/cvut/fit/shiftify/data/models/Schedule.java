package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(generateConstructors = false, generateGettersSetters = false)
public class Schedule {
    @Id(autoincrement = true)
    protected Long id;

    @NotNull
    protected Long userId;
    @NotNull
    protected Long scheduleTypeId;
    @NotNull
    protected Date from;
    protected Date to;
    @NotNull
    protected Integer startingDayOfScheduleCycle;

    public Schedule() {
    }

    public Schedule(Long userId, Long scheduleTypeId, Date from, Date to, Integer startingDayOfScheduleCycle) {
        setUserId(userId);
        setScheduleTypeId(scheduleTypeId);
        setFrom(from);
        setTo(to);
        setStartingDayOfScheduleCycle(startingDayOfScheduleCycle);
    }

    public Schedule(Long id, Long userId, Long scheduleTypeId, Date from, Date to,
                    Integer startingDayOfScheduleCycle) {
        this.id = id;
        this.userId = userId;
        this.scheduleTypeId = scheduleTypeId;
        this.from = from;
        this.to = to;
        this.startingDayOfScheduleCycle = startingDayOfScheduleCycle;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }

    public Long getScheduleTypeId() {
        return this.scheduleTypeId;
    }

    public void setScheduleTypeId(long scheduleTypeId) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setScheduleTypeId(Long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }
}
