package cz.cvut.fit.shiftify.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by petr on 11/20/16.
 */

public class CalendarUtils {

    private static SimpleDateFormat viewFormat = new SimpleDateFormat("dd. MM. yyyy");

    public static String calendarToViewString(Calendar calendar) {
        return viewFormat.format(calendar.getTime());
    }


    public static Calendar getCalendar(String dateString) throws ParseException {
        Date date = viewFormat.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
