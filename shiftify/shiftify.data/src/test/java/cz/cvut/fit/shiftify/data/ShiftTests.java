package cz.cvut.fit.shiftify.data;

import junit.framework.Assert;

import org.junit.Test;

import java.util.GregorianCalendar;

import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_DateTime_Converter;
import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_Date_Converter;
import cz.cvut.fit.shiftify.data.DaoConverters.GregCal_Time_Converter;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;

/**
 * Created by lukas on 16.11.2016.
 */

public class ShiftTests {
    @Test
    public void GetFromToString() throws Exception {
        ScheduleShift shift = new ScheduleShift("name", Utilities.GregCalFrom(6, 0), Utilities.GregCalFrom(14, 30), 8);
        String fromToStr = shift.getFromToString();
        Assert.assertEquals("6.00 - 20.30", fromToStr);
    }
}