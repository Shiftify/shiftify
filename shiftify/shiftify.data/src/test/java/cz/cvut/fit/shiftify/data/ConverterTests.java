package cz.cvut.fit.shiftify.data;

import cz.cvut.fit.shiftify.data.DaoConverters.*;
import cz.cvut.fit.shiftify.data.Utilities.*;
import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by lukas on 16.11.2016.
 */

public class ConverterTests {
    @Test
    public void GregCal_Date_Converter() throws Exception {
        GregCal_Date_Converter conv = new GregCal_Date_Converter();
        GregorianCalendar cal = conv.convertToEntityProperty("1945-05-01");
        Assert.assertEquals(new GregorianCalendar(1945, 4, 1).getTimeInMillis(), cal.getTimeInMillis());
        cal = conv.convertToEntityProperty("2016-02-29");
        Assert.assertEquals(new GregorianCalendar(2016, 1, 29).getTimeInMillis(), cal.getTimeInMillis());

        String timeStr = conv.convertToDatabaseValue(new GregorianCalendar(2016, 1, 29));
        Assert.assertEquals("2016-02-29", timeStr);
        timeStr = conv.convertToDatabaseValue(new GregorianCalendar(1945, 4, 1));
        Assert.assertEquals("1945-05-01", timeStr);
    }

    @Test
    public void GregCal_DateTime_Converter() throws Exception {
        GregCal_DateTime_Converter conv = new GregCal_DateTime_Converter();
        GregorianCalendar cal = conv.convertToEntityProperty("1945-05-01 00:00:00.000");
        Assert.assertEquals(new GregorianCalendar(1945, 4, 1, 0, 0, 0).getTimeInMillis(), cal.getTimeInMillis());
        cal = conv.convertToEntityProperty("2016-02-29 15:28:35.000");
        Assert.assertEquals(new GregorianCalendar(2016, 1, 29, 15, 28, 35).getTimeInMillis(), cal.getTimeInMillis());

        String timeStr = conv.convertToDatabaseValue(new GregorianCalendar(2016, 1, 29, 0, 0, 0));
        Assert.assertEquals("2016-02-29 00:00:00.000", timeStr);
        timeStr = conv.convertToDatabaseValue(new GregorianCalendar(1945, 4, 1, 15, 28, 35));
        Assert.assertEquals("1945-05-01 15:28:35.000", timeStr);
    }

    @Test
    public void GregCal_Time_Converter() throws Exception {
        GregCal_Time_Converter conv = new GregCal_Time_Converter();
        GregorianCalendar cal = conv.convertToEntityProperty("00:00:00.000");
        Assert.assertEquals(Utilities.GregCalFrom(0, 0, 0, 0).getTimeInMillis(), cal.getTimeInMillis());
        cal = conv.convertToEntityProperty("15:28:35.255");
        Assert.assertEquals(Utilities.GregCalFrom(15, 28, 35, 255).getTimeInMillis(), cal.getTimeInMillis());

        String timeStr = conv.convertToDatabaseValue(Utilities.GregCalFrom(15, 28, 35, 255));
        Assert.assertEquals("15:28:35.255", timeStr);
        timeStr = conv.convertToDatabaseValue(Utilities.GregCalFrom(0, 0, 0, 0));
        Assert.assertEquals("00:00:00.000", timeStr);
    }
}