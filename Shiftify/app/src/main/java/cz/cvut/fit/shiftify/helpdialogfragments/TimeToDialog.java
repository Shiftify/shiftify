package cz.cvut.fit.shiftify.helpdialogfragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by petr on 12/11/16.
 */

public class TimeToDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private int mHour;
    private int mMinute;
    private TimeToDialogCallback mCallback;

    public TimeToDialog(Calendar calendar) {
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (TimeToDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " + TimeToDialogCallback.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public TimeToDialog() {
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    public TimeToDialog(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this,
                mHour, mMinute, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mCallback.setTimeTo(hourOfDay, minute);
    }

    public interface TimeToDialogCallback {
        public void setTimeTo(int hour, int minute);
    }
}
