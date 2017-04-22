package cz.cvut.fit.shiftify.helpdialogfragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Calendar;

import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by petr on 11/20/16.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String DATE_TYPE_ARG = "date_type_fragment";
    public static final String SELECTED_DATE = "selected_date";
    public static final String DATE_FROM_FRAGMENT = "date_from_fragment";
    public static final String DATE_TO_FRAGMENT = "date_to_fragment";
    public static final String DATE_PICKER_DIALOG_TYPE = "type";


    private Calendar selectedDate;
    private DateDialogCallback mCallback;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle selectedDateBundle = getArguments();

        if (selectedDateBundle != null) {
            String dateString = selectedDateBundle.getString(SELECTED_DATE);
            selectedDate = CalendarUtils.getCalendarFromDateString(dateString);
            type = selectedDateBundle.getString(DATE_PICKER_DIALOG_TYPE);
        } else {
            selectedDate = null;
        }
    }

    @SuppressWarnings("deprecation") //for backward compatibility, dont touch ffs. More at: https://code.google.com/p/android/issues/detail?id=183358
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (DateDialogCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " + DateDialogCallback.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
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


    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(y, m, d);
        LocalDate date = LocalDate.fromCalendarFields(tmpCal);
        mCallback.onDateSet(date, type);
    }

    public interface DateDialogCallback {
        void onDateSet(LocalDate pickedDate, String datepickerType);
    }
}
