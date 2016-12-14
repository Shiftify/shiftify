package cz.cvut.fit.shiftify.data;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static cz.cvut.fit.shiftify.data.DbTable.QueryCondition;
import static cz.cvut.fit.shiftify.data.DbTable.QueryCondition.ComparisonType;
import static org.junit.Assert.assertEquals;

/**
 * Created by lukas on 16.11.2016.
 */

public class UtilitiesTests {
    @Test
    public void GregCalToStrTest() throws Exception {
        GregorianCalendar calendar = new GregorianCalendar(2016, 0, 31, 2, 0, 30);
        String tmp = Utilities.GregCalToStr(calendar);
        assertEquals("2016-01-31 02:00:30.000", Utilities.GregCalToStr(calendar));
        // To get current date and time use this here below:
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
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
        return;
    }
}