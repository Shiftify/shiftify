package cz.cvut.fit.shiftify.exceptions;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.util.Calendar;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.helpdialogfragments.DateDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeDialog;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.MyTimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionEditActivity extends AppCompatActivity implements DateDialog.DateDialogCallback, TimeDialog.TimeDialogCallback {

    private TextView mDate;
    private TextView mTimeFromEditText;
    private TextView mTimeToEditText;
    private RadioButton mFreeRadioBtn;
    private RadioButton mWorkRadioBtn;

    private ExceptionShift mException;
    private UserManager mUserManager;
    private int mTimeFrom;
    private int mTimeTo;

    private static final String DATE_FRAGMENT = "date_fragment";
    private static final String TIME_TO_FRAGMENT = "time_to_fragment";
    private static final String TIME_FROM_FRAGMENT = "time_from_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_edit);

        Intent intent = getIntent();
        long exceptionId = intent.getLongExtra(ExceptionListActivity.EXCEPTION_ID, -1);
        long userId = intent.getLongExtra(ExceptionListActivity.USER_ID, 0);

        if (exceptionId == -1) {
            ToolbarUtils.setToolbar(this, R.string.exception_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }

        mUserManager = new UserManager();
        initException(exceptionId, userId);

        mDate = (TextView) findViewById(R.id.date_text);
        mTimeFromEditText = (TextView) findViewById(R.id.time_from_text);
        mTimeToEditText = (TextView) findViewById(R.id.time_to_text);
        mFreeRadioBtn = (RadioButton) findViewById(R.id.free_radio_btn);
        mWorkRadioBtn = (RadioButton) findViewById(R.id.work_radio_btn);
        initFields();

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = DateDialog.newInstance();
                Bundle bundle = new Bundle();
                Calendar calendar = CalendarUtils.getCalendarFromDateString(mDate.getText().toString());
                bundle.putLong(DateDialog.DATE_MS_ARG, calendar.getTimeInMillis());
                bundle.putLong(DateDialog.DATE_MS_ARG, Calendar.getInstance().getTimeInMillis());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), DATE_FRAGMENT);
            }
        });

        mTimeFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(TIME_FROM_FRAGMENT, mTimeFrom);
            }
        });

        mTimeToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(TIME_TO_FRAGMENT, mTimeTo);
            }
        });

        mWorkRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mException.setIsWorking(true);
            }
        });

        mFreeRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mException.setIsWorking(false);
            }
        });

    }

    private void initException(long exceptionId, long userId) {
        if (exceptionId == -1) {
            mException = new ExceptionShift();
            mException.setFrom(LocalTime.now());
            mException.setIsWorking(true);
        } else {
            try {
                mException = mUserManager.getExceptionShift(exceptionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Calendar calendar = Calendar.getInstance();
        mTimeFrom = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        mTimeFrom = mTimeTo;
    }

    private void showTimeDialog(String type, int seconds) {
        DialogFragment dialogFragment = TimeDialog.newInstance();
        Bundle args = new Bundle();
        args.putInt(TimeDialog.TIME_ARG, seconds);
        args.putString(TimeDialog.TIME_TYPE_ARG, type);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), type);
    }

    private void setDateText(LocalTime time) {
        mDate.setText(time.toString());

    }

    private void setTimeFromText(int minutes) {
        mTimeFromEditText.setText(MyTimeUtils.timeToString(minutes));
    }

    private void setTimeToText(int minutes) {
        mTimeToEditText.setText(MyTimeUtils.timeToString(minutes));
    }

    private void initFields() {
        setDateText(mException.getFrom());
        setTimeFromText(mTimeFrom);
        setTimeToText(mTimeTo);
        setRadioButtons(mException.getIsWorking());
    }

    private void setRadioButtons(boolean isWorkingException) {
        if (isWorkingException) {
            mWorkRadioBtn.setChecked(true);
        } else {
            mFreeRadioBtn.setChecked(true);
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
//          TODO: Save exception shift into exceptionInSchedule after created methods in user manager
                new CustomSnackbar(this, R.string.feature_in_dev).show();
//                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDateSet(DateTime calendar, String datepickerType) {
        setDateText(calendar.toLocalTime());
        mException.setFrom(calendar.toLocalTime());
    }

    @Override
    public void onTimeSet(int minutes, String timeType) {
        switch (timeType) {
            case TIME_FROM_FRAGMENT:
                mTimeFrom = minutes;
                setTimeFromText(mTimeFrom);
                break;
            case TIME_TO_FRAGMENT:
                mTimeTo = minutes;
                setTimeToText(mTimeTo);
                break;
        }
    }
}
