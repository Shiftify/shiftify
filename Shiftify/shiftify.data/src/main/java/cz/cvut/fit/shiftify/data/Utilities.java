package cz.cvut.fit.shiftify.data;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

/**
 * Created by lukas on 08.12.2016.
 */

public class Utilities {
    public static PeriodFormatter PERIOD_FORMATTER = PeriodFormat.getDefault();
    public static DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm:ss.SSS");
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    //Do not initialize
    private Utilities() {
    }
}
