package cz.cvut.fit.shiftify.schedules;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by petr on 11/20/16.
 */

public class DateToDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    private Calendar mCalendar;
    private DateToDialogCallback mCallback;

    public DateToDialog() {
        mCalendar = Calendar.getInstance();
    }

    public DateToDialog(Calendar calendar) {
        mCalendar = calendar;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (DateToDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DateFromDialogCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        mCallback.setDateTo(calendar);
    }

    public interface DateToDialogCallback {
        public void setDateTo(Calendar calendar);
    }
}
