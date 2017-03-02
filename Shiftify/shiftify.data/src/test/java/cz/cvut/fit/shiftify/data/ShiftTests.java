package cz.cvut.fit.shiftify.data;

import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.Shift;

/**
 * Created by lukas on 16.11.2016.
 */

public class ShiftTests {
    @Test
    public void ShiftPersistsIntoNextDayTest() {
        Shift shift = new ScheduleShift("blah", new LocalTime(14, 0), new Period(10, 0, 0, 0), 1);

        assertFalse(shift.persistsIntoNextDay());
        shift.setFrom(new LocalTime(0, 0));
        assertFalse(shift.persistsIntoNextDay());
        shift.setFrom(new LocalTime(18, 0));
        assertTrue(shift.persistsIntoNextDay());
    }
}