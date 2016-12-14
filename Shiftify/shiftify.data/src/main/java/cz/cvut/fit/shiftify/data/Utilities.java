package cz.cvut.fit.shiftify.data;

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
    public static String GregCalToStr(GregorianCalendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }
    public static String GregCalToStr(GregorianCalendar calendar, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }
    public static <K> K logicalOr(K first, K second) {
        return (first == null ? second : first);
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
