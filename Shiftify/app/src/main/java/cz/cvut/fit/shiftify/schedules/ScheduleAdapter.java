package cz.cvut.fit.shiftify.schedules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by petr on 11/14/16.
 */

public class ScheduleAdapter extends ArrayAdapter<Schedule> {


    private final Context mContext;

    public ScheduleAdapter(Context context, int resource, Schedule[] objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule schedule = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_schedule, parent, false);

        ((TextView) view.findViewById(R.id.schedule_title)).setText(getScheduleTitle(schedule));
        return view;
    }

    private String getScheduleTitle(Schedule schedule) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(schedule.getFrom());
//        TODO After upgraded managers -> schedule.getScheduleType.getName();
//        String res = schedule.getScheduleType.getName();
        String res = "";
        res += CalendarUtils.calendarToDateString(calendar) + " - ";
        if (schedule.getTo() != null) {
            calendar.setTime(schedule.getTo());
            res += CalendarUtils.calendarToDateString(calendar);
        } else {
            res += mContext.getString(R.string.undefinite_time);
        }
        return res;
    }
}
