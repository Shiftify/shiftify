package cz.cvut.fit.shiftify.data;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Created by lukas on 16.11.2016.
 */

public class UtilitiesTests {
    @Test
    public void GregCal_Str_ConvertingTest() throws Exception {
        GregorianCalendar calendar = new GregorianCalendar(2016, 0, 31, 2, 0, 30);
        String tmp = Utilities.GregCalToStr(calendar);
        assertEquals("2016-01-31 02:00:30.000", Utilities.GregCalToStr(calendar));
        assertEquals(calendar.getTimeInMillis(),
                Utilities.StrToGregCal("2016-01-31 02:00:30.000").getTimeInMillis());
        assertEquals("2016-01-31",
                Utilities.GregCalToStr(Utilities.StrToGregCal("2016-01-31", Utilities.CalType.DATE),
                        Utilities.CalType.DATE));
        assertEquals("02:00:30.000",
                Utilities.GregCalToStr(Utilities.StrToGregCal("02:00:30.000", Utilities.CalType.TIME),
                        Utilities.CalType.TIME));
        // To get current date and time use this here below:
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        // To calculate with time only, one would have to use GregCal like this:
        calendar = new GregorianCalendar(1970, 0, 1, 1, 0, 0);
        tmp = Utilities.GregCalToStr(calendar);
        long time = calendar.getTimeInMillis();
        String res = Utilities.GregCalToStr(calendar);
        int year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH),
            day = calendar.get(Calendar.DAY_OF_MONTH),
            hour = calendar.get(Calendar.HOUR_OF_DAY),
            minute = calendar.get(Calendar.MINUTE),
            second = calendar.get(Calendar.SECOND),
            millisecond = calendar.get(Calendar.MILLISECOND);
        calendar = null;
        assertEquals(null, Utilities.GregCalToStr(calendar));
        assertEquals(null, Utilities.StrToGregCal(null));
        return;
    }

    @Test
    public void GregCal_ComputationTest() throws Exception {
        GregorianCalendar calendar = new GregorianCalendar();
        String initStr = Utilities.GregCalToStr(calendar);
        long init = calendar.getTimeInMillis();
        calendar.setTimeInMillis(-3600000L); // 1970-01-01 00:00:00.000
        calendar.setTimeInMillis(0L);        // 1970-01-01 01:00:00.000
        String nextStr = Utilities.GregCalToStr(calendar, Utilities.CalType.TIME);
    }
}