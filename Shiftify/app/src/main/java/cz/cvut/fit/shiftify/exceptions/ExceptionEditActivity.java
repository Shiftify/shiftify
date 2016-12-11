package cz.cvut.fit.shiftify.exceptions;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.util.Calendar;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.helpdialogfragments.DateFromDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.DateToDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeFromDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeToDialog;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.MyTimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionEditActivity extends AppCompatActivity implements DateFromDialog.DateFromDialogCallback, DateToDialog.DateToDialogCallback, TimeFromDialog.TimeFromDialogCallback, TimeToDialog.TimeToDialogCallback {

    private EditText mDateFromEditText;
    private EditText mDateToEditText;
    private EditText mTimeFromEditText;
    private EditText mTimeToEditText;

    private static final String DATE_FROM_FRAGMENT = "date_from_fragment";
    private static final String DATE_TO_FRAGMENT = "date_to_fragment";
    private static final String TIME_TO_FRAGMENT = "time_to_fragment";
    private static final String TIME_FROM_FRAGMENT = "time_from_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_edit);

        Intent intent = getIntent();
        int exceptionId = intent.getIntExtra(ExceptionListActivity.EXCEPTION_ID, 0);
        int userId = intent.getIntExtra(ExceptionListActivity.USER_ID, 0);

        if (exceptionId == 0) {
            ToolbarUtils.setToolbar(this, R.string.exception_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }

        Calendar calendar = Calendar.getInstance();

        mDateFromEditText = (EditText) findViewById(R.id.date_from_text);
        mDateToEditText = (EditText) findViewById(R.id.date_to_text);
        mTimeFromEditText = (EditText) findViewById(R.id.time_from_text);
        mTimeToEditText = (EditText) findViewById(R.id.time_to_text);
        initEditTexts(calendar, null);


        mDateFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendarFromDate(mDateFromEditText.getText().toString());
                    dialogFragment = new DateFromDialog(calendar);
                } catch (ParseException e) {
                    dialogFragment = new DateFromDialog();
                }
                dialogFragment.show(getFragmentManager(), DATE_FROM_FRAGMENT);
            }
        });

        mDateToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendarFromDate(mDateFromEditText.getText().toString());
                    dialogFragment = new DateToDialog(calendar);
                } catch (ParseException e) {
                    dialogFragment = new DateToDialog();
                }
                dialogFragment.show(getFragmentManager(), DATE_TO_FRAGMENT);
            }
        });

        mTimeFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new TimeFromDialog();
                dialogFragment.show(getFragmentManager(), TIME_FROM_FRAGMENT);
            }
        });

        mTimeToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new TimeToDialog();
                dialogFragment.show(getFragmentManager(), TIME_TO_FRAGMENT);
            }
        });

    }

    private void setDateFromText(Calendar calendar) {
        mDateFromEditText.setText(CalendarUtils.calendarToDateString(calendar));

    }

    private void setDateToText(Calendar calendar) {
        mDateToEditText.setText(CalendarUtils.calendarToDateString(calendar));
    }

    private void setTimeFromText(Calendar calendar) {
        mTimeFromEditText.setText(MyTimeUtils.timeToString(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)));
    }

    private void setTimeToText(Calendar calendar) {
        mTimeToEditText.setText(MyTimeUtils.timeToString(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)));
    }

    private void initEditTexts(Calendar start, Calendar end) {
        if (start == null || end == null) {
            setDateFromText(start);
            setTimeFromText(start);
            start.add(Calendar.DATE, 4);
            setDateToText(start);
            setTimeToText(start);
        } else {

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setDateFrom(Calendar calendar) {
        mDateFromEditText.setText(CalendarUtils.calendarToDateString(calendar));
    }

    @Override
    public void setDateTo(Calendar calendar) {
        mDateToEditText.setText(CalendarUtils.calendarToDateString(calendar));
    }

    @Override
    public void setTimeFrom(int hour, int minute) {
        mTimeFromEditText.setText(MyTimeUtils.timeToString(hour, minute));
    }

    @Override
    public void setTimeTo(int hour, int minute) {
        mTimeToEditText.setText(MyTimeUtils.timeToString(hour, minute));
    }
}
