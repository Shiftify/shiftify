package cz.cvut.fit.shiftify.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by petr on 11/20/16.
 */

public class CalendarUtils {
    public static DateTimeFormatter JODA_DATE_FORMATTER = DateTimeFormat.forPattern("dd. MM. yyyy");

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy");

    public static String getStringFromDateCalendar(Calendar calendar) {
        return dateFormat.format(calendar.getTime());
    }

    public static Calendar getCalendarFromDateString(String dateString) {
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public static int secondsOfDay(LocalTime time) {
        int seconds = 0;
        seconds += time.getHourOfDay() * 60 * 60;
        seconds += time.getMinuteOfHour() * 60;
        seconds += time.getSecondOfMinute();

        return seconds;
    }

}
