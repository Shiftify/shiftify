package cz.cvut.fit.shiftify.exceptions;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.helpdialogfragments.DateDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeFromDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeToDialog;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.MyTimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionEditActivity extends AppCompatActivity implements DateDialog.DateDialogCallback, TimeFromDialog.TimeFromDialogCallback, TimeToDialog.TimeToDialogCallback {

    private TextView mDate;
    private TextView mTimeFromEditText;
    private TextView mTimeToEditText;

    private static final String DATE_FRAGMENT = "date_fragment";
    private static final String TIME_TO_FRAGMENT = "time_to_fragment";
    private static final String TIME_FROM_FRAGMENT = "time_from_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_edit);

        Intent intent = getIntent();
        long exceptionId = intent.getLongExtra(ExceptionListActivity.EXCEPTION_ID, 0);
        long userId = intent.getLongExtra(ExceptionListActivity.USER_ID, 0);

        if (exceptionId == 0) {
            ToolbarUtils.setToolbar(this, R.string.exception_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }

        Calendar calendar = Calendar.getInstance();

        mDate = (TextView) findViewById(R.id.date_text);
        mTimeFromEditText = (TextView) findViewById(R.id.time_from_text);
        mTimeToEditText = (TextView) findViewById(R.id.time_to_text);
        initEditTexts(calendar, null);


        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendarFromDate(mDate.getText().toString());
//                    dialogFragment = new DateDialog(calendar);
                } catch (ParseException e) {
//                    dialogFragment = new DateDialog();
                }
//               dialogFragment.show(getFragmentManager(), DATE_FRAGMENT);
            }
        });

        mTimeFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c = MyTimeUtils.StringToTime(mTimeFromEditText.getText().toString());
                DialogFragment dialogFragment = new TimeFromDialog(c);
                dialogFragment.show(getFragmentManager(), TIME_FROM_FRAGMENT);
            }
        });

        mTimeToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c = MyTimeUtils.StringToTime(mTimeToEditText.getText().toString());
                DialogFragment dialogFragment = new TimeToDialog(c);
                dialogFragment.show(getFragmentManager(), TIME_TO_FRAGMENT);
            }
        });

    }

    private void setDateFromText(Calendar calendar) {
        mDate.setText(CalendarUtils.calendarToDateString(calendar));

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_item:
                finish();
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onDateSet(Calendar calendar) {
//        mDate.setText(CalendarUtils.calendarToDateString(calendar));
//    }

    @Override
    public void setTimeFrom(int hour, int minute) {
        mTimeFromEditText.setText(MyTimeUtils.timeToString(hour, minute));
    }

    @Override
    public void setTimeTo(int hour, int minute) {
        mTimeToEditText.setText(MyTimeUtils.timeToString(hour, minute));
    }

    @Override
    public void onDateSet(Calendar calendar, String datepickerType) {

    }
}
