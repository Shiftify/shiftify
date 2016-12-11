package cz.cvut.fit.shiftify.helpdialogfragments;

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

public class DateFromDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar mCalendar;
    private DateFromDialogCallback mCallback;

    public DateFromDialog() {
        mCalendar = Calendar.getInstance();
    }

    public DateFromDialog(Calendar calendar) {
        mCalendar = calendar;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (DateFromDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " + DateFromDialogCallback.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        mCallback.setDateFrom(calendar);
    }

    public interface DateFromDialogCallback {
        public void setDateFrom(Calendar calendar);
    }
}
