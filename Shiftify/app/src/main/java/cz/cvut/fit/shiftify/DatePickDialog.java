package cz.cvut.fit.shiftify;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

import java.util.Calendar;

import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by Vojta on 19.11.2016.
 */

public class DatePickDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String DATE_PICKER_TAG = "datePicker";
    public static final String SELECTED_DATE = "selected_date";

    private DatePickDialogCallback mCallback;
    private Calendar selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle selectedDateBundle = getArguments();

        if (selectedDateBundle != null) {
            String dateString = selectedDateBundle.getString(SELECTED_DATE);
            selectedDate = CalendarUtils.getCalendarFromDateString(dateString);
        } else {
            selectedDate = null;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year, month, day;
        if (selectedDate != null) {
            year = selectedDate.get(Calendar.YEAR);
            month = selectedDate.get(Calendar.MONTH);
            day = selectedDate.get(Calendar.DAY_OF_MONTH);
        } else {
            final Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @SuppressWarnings("deprecation") //for backward compatibility, dont touch ffs. More at: https://code.google.com/p/android/issues/detail?id=183358
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (DatePickDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DatePickDialogCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(y, m, d);
        LocalDate date = LocalDate.fromCalendarFields(tmpCal);
        mCallback.onDateSet(date);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public interface DatePickDialogCallback {
        void onDateSet(LocalDate pickedDate);
    }

}
