package cz.cvut.fit.shiftify.utils;

/**
 * Created by petr on 12/11/16.
 */

public class MyTimeUtils {

    public static String timeToString(int hour, int minute) {
        String res = "";
        if (hour < 10) {
            res += "0";
        }
        res += String.valueOf(hour) + ":";
        if (minute < 10) {
            res += "0";
        }
        res += String.valueOf(minute);
        return res;
    }

    public static int[] StringToTime(String time) {
        int[] res = {0, 0};
        return res;
    }
}
