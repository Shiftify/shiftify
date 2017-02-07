package cz.cvut.fit.shiftify.helpdialogfragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

import cz.cvut.fit.shiftify.utils.MyTimeUtils;

/**
 * Created by petr on 12/11/16.
 */

public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String TIME_TYPE_ARG = "time_type_fragment";
    public static final String TIME_ARG = "time_minutes";

    private TimeDialogCallback mCallback;
    private String mType;

    public static TimeDialog newInstance() {
        Bundle args = new Bundle();
        TimeDialog fragment = new TimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

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
        Calendar calendar = Calendar.getInstance();
        Bundle args = getArguments();
        int minutes = args.getInt(TIME_ARG, calendar.get(Calendar.MINUTE));
        mType = args.getString(TIME_TYPE_ARG);
        return new TimePickerDialog(getActivity(), this,
                MyTimeUtils.getHour(minutes), MyTimeUtils.getMinutes(minutes), true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        mCallback.onTimeSet(minute + hourOfDay * 60, mType);
    }

    public interface TimeDialogCallback {
        public void onTimeSet(int minutes, String timeType);
    }
}
