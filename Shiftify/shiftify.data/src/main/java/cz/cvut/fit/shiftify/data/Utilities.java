package cz.cvut.fit.shiftify.data;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lukas on 08.12.2016.
 */

public abstract class Utilities {
    public enum CalType {
        DATE,      // "yyyy-MM-dd"
        TIME,      // "HH:mm:ss.SSS"
        DATETIME   // "yyyy-MM-dd HH:mm:ss.SSS"
    }

    public static String GregCalToStr(GregorianCalendar calendar) {
        return GregCalToStr(calendar, CalType.DATETIME);
    }
    public static String GregCalToStr(GregorianCalendar calendar, CalType type) {
        if (type == CalType.DATE)
            return GregCalToStr(calendar, "yyyy-MM-dd");
        if (type == CalType.TIME)
            return GregCalToStr(calendar, "HH:mm:ss.SSS");
        if (type == CalType.DATETIME)
            return GregCalToStr(calendar, "yyyy-MM-dd HH:mm:ss.SSS");
        return null;
    }
    public static String GregCalToStr(GregorianCalendar calendar, String format) {
        if (calendar == null) return null;
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }

    public static GregorianCalendar StrToGregCal(String str) {
        return StrToGregCal(str, CalType.DATETIME);
    }
    public static GregorianCalendar StrToGregCal(String str, CalType type) {
        if (type == CalType.DATE)
            return StrToGregCal(str, "yyyy-MM-dd");
        if (type == CalType.TIME)
            return StrToGregCal(str, "HH:mm:ss.SSS");
        if (type == CalType.DATETIME)
            return StrToGregCal(str, "yyyy-MM-dd HH:mm:ss.SSS");
        return null;
    }
    public static GregorianCalendar StrToGregCal(String str, String format) {
        if (str == null) return null;
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(calendar);
        calendar.setTime(fmt.parse(str, new ParsePosition(0)));
        return calendar;
    }
    public static GregorianCalendar GregCalFrom(int hours, int minutes) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        return calendar;
    }
    public static GregorianCalendar GregCalFrom(int hours, int minutes, int seconds, int milliseconds) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return calendar;
    }
    public static GregorianCalendar GregCalFromMillis(long milliseconds) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(milliseconds);
        return calendar;
    }
    public static GregorianCalendar GregCalFromGregCal(GregorianCalendar orig) {
        if (orig == null)
            return null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(orig.getTimeInMillis());
        return calendar;
    }

    public static GregorianCalendar GregCalSimplifyToType(GregorianCalendar cal, CalType type) {
        if (type == CalType.DATETIME && cal != null)
            return cal;
        if (type == CalType.DATE)
            return GregCalDateOnly(cal);
        if (type == CalType.TIME)
            return GregCalTimeOnly(cal);
        return null;
    }
    public static GregorianCalendar GregCalDateOnly(GregorianCalendar cal) {
        if (cal == null) return null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        return calendar;
    }
    public static GregorianCalendar GregCalTimeOnly(GregorianCalendar cal) {
        if (cal == null) return null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, cal.get(Calendar.SECOND));
        return calendar;
    }

    public static GregorianCalendar GregCalTimeSubtraction(GregorianCalendar cal1, GregorianCalendar cal2) {
        int hours1 = cal1.get(Calendar.HOUR_OF_DAY),
            minutes1 = cal1.get(Calendar.MINUTE),
            hours2 = cal2.get(Calendar.HOUR_OF_DAY),
            minutes2 = cal2.get(Calendar.MINUTE),
            mDiff = minutes1 - minutes2,
            hDiff = hours1 - hours2;
        return GregCalFrom(hDiff - (mDiff < 0 ? 1 : 0), (mDiff < 0 ? 60 + mDiff : mDiff));
    }
    public static GregorianCalendar GregCalTimeAddition(GregorianCalendar cal1, GregorianCalendar cal2) {
        int hours1 = cal1.get(Calendar.HOUR_OF_DAY),
                minutes1 = cal1.get(Calendar.MINUTE),
                hours2 = cal2.get(Calendar.HOUR_OF_DAY),
                minutes2 = cal2.get(Calendar.MINUTE),
                hDiff = hours1 + hours2,
                mDiff = minutes1 + minutes2;
        return GregCalFrom(hDiff + (mDiff > 59 ? 1 : 0), (mDiff > 59 ? mDiff - 60 : mDiff));
    }

    public static String[] concatStrArrays(String[] first, String[] second) {
        List<String> both = new ArrayList<String>(first.length + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new String[both.size()]);
    }
    public static <K, V> HashMap<K, V> concatHashMaps(HashMap<K, V> first, HashMap<K, V> second) {
        HashMap<K, V> ret = new HashMap<>();
        ret.putAll(first);
        ret.putAll(second);
        return ret;
    }
}
