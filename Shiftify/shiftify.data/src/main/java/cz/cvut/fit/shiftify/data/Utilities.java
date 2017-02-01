package cz.cvut.fit.shiftify.data;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

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
        calendar.setTimeInMillis(1000 * ((minutes * 60) + (hours * 3600)));
        return calendar;
    }
    public static GregorianCalendar GregCalFrom(int hours, int minutes, int seconds, int milliseconds) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(milliseconds + 1000 * (seconds + (minutes * 60) + (hours * 3600)));
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
            return GregCalFromMillis(cal.getTimeInMillis());
        if (type == CalType.DATE)
            return GregCalDateOnly(cal);
        if (type == CalType.TIME)
            return GregCalTimeOnly(cal);
        return null;
    }
    public static GregorianCalendar GregCalDateOnly(GregorianCalendar cal) {
        if (cal == null) return null;
        return GregCalFromMillis(StrToGregCal(GregCalToStr(cal, CalType.DATE), CalType.DATE).getTimeInMillis());
    }
    public static GregorianCalendar GregCalTimeOnly(GregorianCalendar cal) {
        if (cal == null) return null;
        return GregCalFromMillis(StrToGregCal(GregCalToStr(cal, CalType.TIME), CalType.TIME).getTimeInMillis());
    }

    public static GregorianCalendar GregCalSubtractionToGregCal(GregorianCalendar cal1, GregorianCalendar cal2, CalType type) {
        return GregCalSubtractionToGregCal(cal1, type, cal2, type);
    }
    public static GregorianCalendar GregCalSubtractionToGregCal(GregorianCalendar cal1, CalType type1, GregorianCalendar cal2, CalType type2) {
        GregorianCalendar calendar1 = GregCalSimplifyToType(cal1, type1),
            calendar2 = GregCalSimplifyToType(cal2, type2);
        if (calendar1 != null && calendar2 != null)
            return GregCalSimplifyToType(GregCalFromMillis(Math.abs(calendar1.getTimeInMillis() - calendar2.getTimeInMillis())), type1);
        return null;
    }

    public static GregorianCalendar GregCalAdditionToGregCal(GregorianCalendar cal1, GregorianCalendar cal2, CalType type) {
        return GregCalAdditionToGregCal(cal1, type, cal2, type);
    }
    public static GregorianCalendar GregCalAdditionToGregCal(GregorianCalendar cal1, CalType type1, GregorianCalendar cal2, CalType type2) {
        GregorianCalendar calendar1 = GregCalSimplifyToType(cal1, type1),
                calendar2 = GregCalSimplifyToType(cal2, type2);
        if (calendar1 != null && calendar2 != null)
            return GregCalSimplifyToType(GregCalFromMillis(Math.abs(calendar1.getTimeInMillis() - calendar2.getTimeInMillis())), type1);
        return null;
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
