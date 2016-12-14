package cz.cvut.fit.shiftify.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by petr on 11/20/16.
 */

public class CalendarUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy");

    public static String calendarToDateString(Calendar calendar) {
        return dateFormat.format(calendar.getTime());
    }

    public static Calendar getCalendarFromDate(String dateString) throws ParseException {
        Date date = dateFormat.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
