package cz.cvut.fit.shiftify.exceptions;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.Calendar;

import cz.cvut.fit.shiftify.DatePickDialog;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeDialog;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.MyTimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionEditActivity extends AppCompatActivity implements TimeDialog.TimeDialogCallback, DatePickDialog.DatePickDialogCallback {

    private static final String DATE_FRAGMENT = "date_fragment";
    private static final String TIME_TO_FRAGMENT = "time_to_fragment";
    private static final String TIME_FROM_FRAGMENT = "time_from_fragment";

    private RadioButton mWorkRadioBtn;
    private RadioButton mFreeRadioBtn;
    private EditText mDescriptionEditText;
    private TextView mDateTextView;
    private ImageButton mCalendarButton;
    private TextView mTimeFromTextView;
    private TextView mTimeToTextView;
    private Toast mToast;

    private Boolean isWorking;
    private LocalDate from;
    private int timeFrom;
    private int timeTo;

    private Long userId;
    private Long exceptionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_edit);

        mDescriptionEditText = (EditText) findViewById(R.id.title_exception);
        mCalendarButton = (ImageButton) findViewById(R.id.exc_btn_cal);
        mDateTextView = (TextView) findViewById(R.id.date_text);
        mTimeFromTextView = (TextView) findViewById(R.id.time_from_text);
        mTimeToTextView = (TextView) findViewById(R.id.time_to_text);
        mFreeRadioBtn = (RadioButton) findViewById(R.id.free_radio_btn);
        mWorkRadioBtn = (RadioButton) findViewById(R.id.work_radio_btn);

        final Intent intent = getIntent();
        if (intent != null) {
            exceptionId = intent.getLongExtra(ExceptionListActivity.EXCEPTION_ID, -1);
            userId = intent.getLongExtra(ExceptionListActivity.USER_ID, 0);
        } else {
            Log.w("ExceptionEditActivity: ", "No user_id/exceptionId was not provided by intent (intent==null)");
            finish();
        }

        if (exceptionId == -1) {
            ToolbarUtils.setToolbar(this, R.string.exception_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }

        mCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newDialogFragment = new DatePickDialog();
                Bundle selectedDataBundle = new Bundle();
                selectedDataBundle.putString(DatePickDialog.SELECTED_DATE, from.toString(CalendarUtils.JODA_DATE_FORMATTER));
                newDialogFragment.setArguments(selectedDataBundle);
                newDialogFragment.show(getFragmentManager(), DatePickDialog.DATE_PICKER_TAG);
            }
        });

        mTimeFromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(TIME_FROM_FRAGMENT, timeFrom);
            }
        });

        mTimeToTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(TIME_TO_FRAGMENT, timeTo);
            }
        });

        mWorkRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isWorking = true;
            }
        });

        mFreeRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isWorking = false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        initFields();
    }

    private void initFields() {
        Calendar calendar = Calendar.getInstance();
        timeFrom = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        timeFrom = timeTo;

        if (exceptionId == -1) {
            isWorking = true;
            from = LocalDate.now();

            mWorkRadioBtn.toggle();
            mDateTextView.setText(from.toString(CalendarUtils.JODA_DATE_FORMATTER));
            setTimeFromText(timeFrom);
            setTimeToText(timeTo);
        } else {
            ExceptionShift exceptionShift;
            try {
                exceptionShift = new UserManager().getExceptionShift(exceptionId);
                if (exceptionShift.getIsWorking()) {
                    mWorkRadioBtn.toggle();
                }
                mDescriptionEditText.setText(exceptionShift.getDescription());
                mDateTextView.setText(exceptionShift.getFrom().toString(CalendarUtils.JODA_DATE_FORMATTER));
                setTimeFromText(timeFrom);
                setTimeToText(timeTo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*private void initException(long exceptionId, long userId) {
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
        timeFrom = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        timeFrom = timeTo;
    }*/

    private void showTimeDialog(String type, int seconds) {
        DialogFragment dialogFragment = TimeDialog.newInstance();
        Bundle args = new Bundle();
        args.putInt(TimeDialog.TIME_ARG, seconds);
        args.putString(TimeDialog.TIME_TYPE_ARG, type);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), type);
    }

    /*private void setDateText(LocalTime time) {
        mDateTextView.setText(time.toString(CalendarUtils.JODA_DATE_FORMATTER));

    }*/

    private void setTimeFromText(int minutes) {
        mTimeFromTextView.setText(MyTimeUtils.timeToString(minutes));
    }

    private void setTimeToText(int minutes) {
        mTimeToTextView.setText(MyTimeUtils.timeToString(minutes));
    }

    /*private void initFields() {
        setDateText(mException.getFrom());
        setTimeFromText(timeFrom);
        setTimeToText(timeTo);
        setRadioButtons(mException.getIsWorking());
    }*/

    /*private void setRadioButtons(boolean isWorkingException) {
        if (isWorkingException) {
            mWorkRadioBtn.setChecked(true);
        } else {
            mFreeRadioBtn.setChecked(true);
        }
    }*/

    private boolean checkConstraints() {
        if (mDescriptionEditText.getText().toString().equals("")) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, R.string.exception_edit_wrong_desctription, Toast.LENGTH_LONG);
            mToast.show();
            return false;
        } else if (timeTo < timeFrom) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, R.string.exception_edit_wrong_period, Toast.LENGTH_LONG);
            mToast.show();
            return false;
        } else {
            return true;
        }
    }

    private Bundle constructResultBundle() {
        Bundle resultBundle = new Bundle();
        resultBundle.putBoolean(ExceptionListActivity.IS_WORKING, isWorking);
        resultBundle.putString(ExceptionListActivity.DESCRIPTION, mDescriptionEditText.getText().toString());
        resultBundle.putString(ExceptionListActivity.FROM, from.toString(CalendarUtils.JODA_DATE_FORMATTER));
        resultBundle.putInt(ExceptionListActivity.TIME_FROM, timeFrom);
        resultBundle.putInt(ExceptionListActivity.TIME_TO, timeTo);
        return resultBundle;
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
                if (!checkConstraints()) {
                    return true;
                } else {
                    Intent resultIntent = new Intent();
                    Bundle resultBundle = constructResultBundle();
                    resultIntent.putExtras(resultBundle);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                    return true;
                }
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTimeSet(int minutes, String timeType) {
        switch (timeType) {
            case TIME_FROM_FRAGMENT:
                timeFrom = minutes;
                setTimeFromText(timeFrom);
                break;
            case TIME_TO_FRAGMENT:
                timeTo = minutes;
                setTimeToText(timeTo);
                break;
        }
    }

    @Override
    public void onDateSet(LocalDate date) {
        String newDate = date.toString(CalendarUtils.JODA_DATE_FORMATTER);
        mDateTextView.setText(newDate);
        from= new LocalDate(date);
    }
}
