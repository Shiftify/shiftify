package cz.cvut.fit.shiftify.data;

import org.junit.Test;
import java.sql.Time;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lukas on 16.11.2016.
 */

public class ShiftTests {
    @Test
    public void persistsIntoNextDay_worksCorrectly() throws Exception {
        ScheduleShift sShift = new ScheduleShift("schedule", new Time(22, 0, 0), new Time(8, 0, 0), 1, 1);
        assertTrue(sShift.persistsIntoNextDay());
        sShift.setDuration(new Time(2, 0, 0));
        assertFalse(sShift.persistsIntoNextDay());
        ExceptionShift eShift = new ExceptionShift(new Time(0, 2, 0), new Time(23, 59, 0), 1);
        assertTrue(eShift.persistsIntoNextDay());
        eShift.setFrom(new Time(8, 0, 0));
        eShift.setDuration(new Time(5, 30, 0));
        assertFalse(eShift.persistsIntoNextDay());
        eShift.setFrom(new Time(26, 0, 0));
        eShift.setDuration(new Time(4, 30, 0));
        assertTrue(eShift.persistsIntoNextDay());
    }
}