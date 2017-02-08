package cz.cvut.fit.shiftify.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static Calendar addDay(Calendar calendar){
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }
    public static GregorianCalendar addDay(GregorianCalendar calendar){
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return (GregorianCalendar)calendar;
    }

    public static Calendar substractDay(Calendar calendar) {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar;
    }

    public static GregorianCalendar substractDay(GregorianCalendar calendar){
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return (GregorianCalendar) calendar;
    }

    public static Calendar addDay(long ms){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        return addDay(calendar);
    }

}
