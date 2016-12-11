package cz.cvut.fit.shiftify.utils;

import java.util.Calendar;

/**
 * Created by petr on 12/11/16.
 */

public class MyTimeUtils {

    private static final String TAG = "MY_TIME_UTILS";

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

    public static Calendar StringToTime(String time) {
        Calendar calendar = Calendar.getInstance();
        String[] nums = time.split(":");
        if (nums.length == 2) {
            int h = Integer.parseInt(nums[0], 10);
            int m = Integer.parseInt(nums[1], 10);
            calendar.set(Calendar.HOUR_OF_DAY, h);
            calendar.set(Calendar.MINUTE, m);
        } else {
            throw new IllegalArgumentException("Bad time format");
        }
        return calendar;
    }
}
