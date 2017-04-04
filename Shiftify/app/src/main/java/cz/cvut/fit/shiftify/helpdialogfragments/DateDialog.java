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
    public static final String DATE_MS_ARG = "date_seconds";
    public static final String DATE_MIN_MS_ARGS = "date_minimum_in_seconds";

    private DateDialogCallback mCallback;

    public static DateDialog newInstance() {
        Bundle args = new Bundle();
        DateDialog fragment = new DateDialog();
        fragment.setArguments(args);
        return fragment;
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
        Bundle args = getArguments();
        Calendar calendar = Calendar.getInstance();
        long milliseconds = args.getLong(DATE_MS_ARG, calendar.getTimeInMillis());
        long minimumMilliseconds = 0;
        calendar.setTimeInMillis(milliseconds);
        if (args.containsKey(DATE_MIN_MS_ARGS)) {
            minimumMilliseconds = args.getLong(DATE_MIN_MS_ARGS);
            if (milliseconds < minimumMilliseconds)
            calendar = CalendarUtils.addDay(minimumMilliseconds);
        }

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setTag(args.getString(DATE_TYPE_ARG));
        if (args.containsKey(DATE_MIN_MS_ARGS)) {
            dialog.getDatePicker().setMinDate(minimumMilliseconds);
        }
        return dialog;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        mCallback.onDateSet(new DateTime(y, m, d, 0, 0), (String) datePicker.getTag());
    }

    public interface DateDialogCallback {
        public void onDateSet(DateTime dateTime, String datepickerType);
    }
}
