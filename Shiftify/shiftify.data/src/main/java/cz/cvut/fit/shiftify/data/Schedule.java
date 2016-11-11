package cz.cvut.fit.shiftify.data;

import java.util.Date;

/**
 * Created by lukas on 11.11.2016.
 */

public class Schedule {
    public Schedule() { }
    public Schedule(Integer scheduleTypeId, Date from, Date to, Integer startingDayOfScheduleCycle) {
        ScheduleTypeId = scheduleTypeId;
        From = from;
        To = to;
        StartingDayOfScheduleCycle = startingDayOfScheduleCycle;
    }

    public Integer Id;
    public Integer ScheduleTypeId;
    public Date From;
    public Date To;
    public Integer StartingDayOfScheduleCycle;
}
