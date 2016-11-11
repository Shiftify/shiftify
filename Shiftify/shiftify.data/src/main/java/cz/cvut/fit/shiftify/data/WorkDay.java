package cz.cvut.fit.shiftify.data;

import java.util.Date;
import java.util.List;

/**
 * Created by lukas on 11.11.2016.
 */

public class WorkDay {
    public WorkDay() { }
    public WorkDay(Date date, List<Shift> shifts) {
        Date = date;
        Shifts = shifts;
    }

    public Date Date;
    public List<Shift> Shifts;
}
