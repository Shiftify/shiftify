package cz.cvut.fit.shiftify.helpdialogfragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

import java.sql.Time;
import java.util.Calendar;

import cz.cvut.fit.shiftify.utils.TimeUtils;

/**
 * Created by petr on 12/11/16.
 */

public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String TIME_TYPE_ARG = "time_type_fragment";
    public static final String TIME_ARG = "local_time";

    private TimeDialogCallback mCallback;
    private String mType;

    public static TimeDialog newInstance() {
        Bundle args = new Bundle();
        TimeDialog fragment = new TimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("deprecation") //for backward compatibility, dont touch ffs. More at: https://code.google.com/p/android/issues/detail?id=183358
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (TimeDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " + TimeDialogCallback.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalTime now = LocalTime.now();
        Bundle args = getArguments();
        String localTimeString = args.getString(TIME_ARG, now.toString(TimeUtils.JODA_TIME_FORMATTER));
        LocalTime localTime = LocalTime.parse(localTimeString, TimeUtils.JODA_TIME_FORMATTER);
        mType = args.getString(TIME_TYPE_ARG);
        return new TimePickerDialog(getActivity(), this,
                localTime.getHourOfDay(), localTime.getMinuteOfHour(), true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        LocalTime localTime = new LocalTime(hourOfDay, minute);
        mCallback.onTimeSet(localTime, mType);
    }

    public interface TimeDialogCallback {
        void onTimeSet(LocalTime localTime, String timeType);
    }
}
