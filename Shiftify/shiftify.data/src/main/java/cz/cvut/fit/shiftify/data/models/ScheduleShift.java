package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.sql.Time;

import cz.cvut.fit.shiftify.data.Shift;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(generateConstructors = false)
public class ScheduleShift extends Shift {

    @Id(autoincrement = true)
    protected Long id;

    @NotNull
    protected Long scheduleTypeId;

    @NotNull
    protected String name;

    @NotNull
    protected Integer dayOfScheduleCycle;


    public ScheduleShift() {
    }

    public ScheduleShift(String name, Time from, Time duration, Integer scheduleTypeId, Integer dayOfScheduleCycle) {
        this(name, from, duration, scheduleTypeId, dayOfScheduleCycle, null);
    }

    public ScheduleShift(String name, Time from, Time duration, Integer scheduleTypeId, Integer dayOfScheduleCycle, String description) {
        super(from, duration, description);
        setName(name);
        setScheduleTypeId(scheduleTypeId);
        setDayOfScheduleCycle(dayOfScheduleCycle);
    }

    public ScheduleShift(Long id, @NotNull Long scheduleTypeId, @NotNull String name, @NotNull Integer dayOfScheduleCycle) {
        this.id = id;
        this.scheduleTypeId = scheduleTypeId;
        this.name = name;
        this.dayOfScheduleCycle = dayOfScheduleCycle;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getScheduleTypeId() {
        return scheduleTypeId;
    }

    public void setScheduleTypeId(long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }

    public Integer getDayOfScheduleCycle() {
        return dayOfScheduleCycle;
    }

    public void setDayOfScheduleCycle(Integer dayOfScheduleCycle) {
        this.dayOfScheduleCycle = dayOfScheduleCycle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScheduleTypeId(Long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }
}
