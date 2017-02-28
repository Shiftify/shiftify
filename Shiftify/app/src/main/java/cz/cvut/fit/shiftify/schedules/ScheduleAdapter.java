package cz.cvut.fit.shiftify.schedules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by petr on 11/14/16.
 */

public class ScheduleAdapter extends ArrayAdapter<Schedule> {


    private final Context mContext;
    private List<Schedule> dataset;
    private int mResource;

    public ScheduleAdapter(Context context, int resource) {
        super(context, resource);
        dataset = new ArrayList<>();
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule schedule = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResource, parent, false);

        ((TextView) view.findViewById(R.id.schedule_title)).setText(getScheduleTitle(schedule));
        return view;
    }

    public void setSchedules(List<Schedule> schedules) {
        dataset.clear();
        dataset.addAll(schedules);
        notifyDataSetChanged();
    }

    public List<Schedule> getSchedules() {
        return dataset;
    }

    private String getScheduleTitle(Schedule schedule) {
        LocalDate today = LocalDate.now();


        String name = schedule.getScheduleType().getName();
        String todayStr = today.toString(CalendarUtils.JODA_DATE_FORMATTER);
        String toStr = (schedule.getTo() == null) ? "neurčito" : schedule.getTo().toString(CalendarUtils.JODA_DATE_FORMATTER);

        String title = MessageFormat.format("{0} {1} - {2}", name, todayStr, toStr);

        return title;
    }

    @Nullable
    @Override
    public Schedule getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }
}
