package cz.cvut.fit.shiftify.helpdialogfragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by petr on 12/11/16.
 */

public class TimeFromDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private int mHour;
    private int mMinute;
    private TimeFromDialogCallback mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (TimeFromDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " + TimeFromDialogCallback.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public TimeFromDialog() {
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    public TimeFromDialog(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
    }

    public TimeFromDialog(Calendar calendar) {
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this,
                mHour, mMinute, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mCallback.setTimeFrom(hourOfDay, minute);
    }

    public interface TimeFromDialogCallback {
        public void setTimeFrom(int hour, int minute);
    }
}
