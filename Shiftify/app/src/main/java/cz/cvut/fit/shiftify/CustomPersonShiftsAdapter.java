package cz.cvut.fit.shiftify;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.Shift;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by Vojta on 07.02.2017.
 */

public class CustomPersonShiftsAdapter extends ArrayAdapter<WorkDay> {

    List<WorkDay> workDayList;
    Activity context;


    public CustomPersonShiftsAdapter(Activity context, List<WorkDay> workDayList) {
        super(context, R.layout.person_shifts_single, workDayList);
        this.workDayList = workDayList;
        this.context = context;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.person_shifts_single, null, true);

        TextView shiftDate = (TextView) rowView.findViewById(R.id.shift_date);
        shiftDate.setText(CalendarUtils.calendarToDateString(workDayList.get(position).getDate()) );

        TextView shiftName = (TextView) rowView.findViewById(R.id.shift_name);

        List<Shift> tmpShiftList = workDayList.get(position).getShifts();
        String tmpShiftStr = new String();

        for (Shift s: tmpShiftList
             ) {
            tmpShiftStr += s.getName() + "  " + s.getFromToString();
        }
        shiftName.setText( tmpShiftStr );


        // TODO NAPLNIT TIMELINEVIEW PRES WORKDAY

        return rowView;
    }





}
