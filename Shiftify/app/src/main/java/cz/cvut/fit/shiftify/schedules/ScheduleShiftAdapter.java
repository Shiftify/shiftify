package cz.cvut.fit.shiftify.schedules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.ScheduleShift;
import cz.cvut.fit.shiftify.data.ScheduleType;

/**
 * Created by petr on 11/14/16.
 */

public class ScheduleShiftAdapter extends ArrayAdapter<ScheduleShift> {

    private Context mContext;

    public ScheduleShiftAdapter(Context context, int resource, ScheduleShift[] objects) {
        super(context, resource, objects);
        mContext = context;
        setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleShift scheduleShift = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.spinner_item, parent, false);
        ((TextView) view.findViewById(R.id.spinnerItemTextView)).setText(scheduleShift.getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }


}
