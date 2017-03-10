package cz.cvut.fit.shiftify.exceptions;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
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

import cz.cvut.fit.shiftify.DatePickDialog;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeDialog;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.TimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/6/16.
 */

public class ExceptionEditActivity extends AppCompatActivity implements TimeDialog.TimeDialogCallback, DatePickDialog.DatePickDialogCallback {

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
    private LocalDate exceptionShiftDate;
    private LocalTime timeFrom;
    private LocalTime timeTo;
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
            exceptionId = intent.getLongExtra(ExceptionListActivity.EXCEPTION_SHIFT_ID, -1);
            if (exceptionId!=-1){
                exceptionShiftDate = LocalDate.parse(intent.getStringExtra(ExceptionListActivity.EXCEPTION_SHIFT_DATE), CalendarUtils.JODA_DATE_FORMATTER);
            }
            else{
                exceptionShiftDate = LocalDate.parse(intent.getStringExtra(ExceptionListActivity.SELECTED_DATE), CalendarUtils.JODA_DATE_FORMATTER);
            }
        } else {
            Log.w("ExceptionEditActivity: ", "No date/exceptionId was not provided by intent (intent==null)");
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
                selectedDataBundle.putString(DatePickDialog.SELECTED_DATE, exceptionShiftDate.toString(CalendarUtils.JODA_DATE_FORMATTER));
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

        if (exceptionId == -1) {
            timeFrom = LocalTime.now();
            timeTo = LocalTime.now();
            isWorking = true;

            mWorkRadioBtn.toggle();
            mDateTextView.setText(exceptionShiftDate.toString(CalendarUtils.JODA_DATE_FORMATTER));
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
                mDateTextView.setText(exceptionShiftDate.toString(CalendarUtils.JODA_DATE_FORMATTER));
                timeFrom = exceptionShift.getFrom();
                timeTo = exceptionShift.getFrom().plus(exceptionShift.getDuration());
                setTimeFromText(timeFrom);
                setTimeToText(timeTo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showTimeDialog(String type, LocalTime localTime) {
        DialogFragment dialogFragment = TimeDialog.newInstance();
        Bundle args = new Bundle();
        args.putString(TimeDialog.TIME_ARG, localTime.toString(TimeUtils.JODA_TIME_FORMATTER));
        args.putString(TimeDialog.TIME_TYPE_ARG, type);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), type);
    }

    private void setTimeFromText(LocalTime timeFrom) {
        mTimeFromTextView.setText(timeFrom.toString(TimeUtils.JODA_TIME_FORMATTER));
    }

    private void setTimeToText(LocalTime timeTo) {
        mTimeToTextView.setText(timeTo.toString(TimeUtils.JODA_TIME_FORMATTER));
    }

    private boolean checkConstraints() {
        if (mDescriptionEditText.getText().toString().equals("")) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, R.string.exception_edit_wrong_desctription, Toast.LENGTH_LONG);
            mToast.show();
            return false;
        } else if (timeTo.getMillisOfDay() < timeFrom.getMillisOfDay()) {
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
        resultBundle.putString(ExceptionListActivity.EXCEPTION_SHIFT_DATE, exceptionShiftDate.toString(CalendarUtils.JODA_DATE_FORMATTER));
        resultBundle.putString(ExceptionListActivity.TIME_FROM, timeFrom.toString(TimeUtils.JODA_TIME_FORMATTER));
        resultBundle.putString(ExceptionListActivity.TIME_TO, timeTo.toString(TimeUtils.JODA_TIME_FORMATTER));
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
    public void onTimeSet(LocalTime localTime, String timeType) {
        switch (timeType) {
            case TIME_FROM_FRAGMENT:
                timeFrom = new LocalTime(localTime);
                setTimeFromText(timeFrom);
                break;
            case TIME_TO_FRAGMENT:
                timeTo = new LocalTime(localTime);
                setTimeToText(timeTo);
                break;
        }
    }

    @Override
    public void onDateSet(LocalDate date) {
        String newDate = date.toString(CalendarUtils.JODA_DATE_FORMATTER);
        mDateTextView.setText(newDate);
        exceptionShiftDate = new LocalDate(date);
    }
}
