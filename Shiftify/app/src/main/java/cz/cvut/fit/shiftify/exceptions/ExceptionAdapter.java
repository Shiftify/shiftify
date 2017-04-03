package cz.cvut.fit.shiftify.exceptions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.TimeUtils;

import static android.R.color.holo_red_light;

/**
 * Created by petr on 11/14/16.
 */

public class ExceptionAdapter extends ArrayAdapter<ExceptionShift> {


    private Context mContext;
    private List<ExceptionShift> dataset;
    private int mResource;


    public ExceptionAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExceptionShift exception = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResource, parent, false);
        String date = exception.getDate().toString(CalendarUtils.JODA_DATE_FORMATTER);
        String startTime = exception.getFrom().toString(TimeUtils.JODA_TIME_FORMATTER);
        String endTime = new LocalTime(exception.getFrom().plus(exception.getDuration())).toString(TimeUtils.JODA_TIME_FORMATTER);
        ((TextView) view.findViewById(R.id.exception_date)).setText(date);
        ((TextView) view.findViewById(R.id.exception_description)).setText(exception.getDescription());
        ((TextView) view.findViewById(R.id.exception_time)).setText("From: " + startTime + "   To: " + endTime);
        if (exception.getIsWorking()){
            (view.findViewById(R.id.person_exception_single_layout)).setBackgroundColor(mContext.getResources().getColor(R.color.colorExceptionShiftWorking));
        }
        else{
            (view.findViewById(R.id.person_exception_single_layout)).setBackgroundColor(mContext.getResources().getColor(R.color.colorExceptionShiftFree));
        }
        return view;
    }

    public void setExceptionShifts(@NonNull List<ExceptionShift> exceptionShifts){
        dataset.clear();
        dataset.addAll(exceptionShifts);
        notifyDataSetChanged();
    }

//    TODO After upgraded DB - set view of exception item in list
    private String getExceptionTitle(ExceptionShift exception) {
        return "ID: " + String.valueOf(exception.getId()) +
                " - " + exception.getFrom().toString(CalendarUtils.JODA_DATE_FORMATTER);
    }

    @Nullable
    @Override
    public ExceptionShift getItem(int position) {
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
