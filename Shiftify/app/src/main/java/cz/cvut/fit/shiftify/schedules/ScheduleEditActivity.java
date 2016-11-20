package cz.cvut.fit.shiftify.schedules;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.ScheduleShift;
import cz.cvut.fit.shiftify.data.ScheduleType;
import cz.cvut.fit.shiftify.data.ScheduleTypeManager;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleEditActivity extends AppCompatActivity implements ScheduleDatePickerDialog.CustomDatePickerDialogCallback {

    private Spinner mScheduleSpinner;
    private Spinner mFirstShiftSpinner;
    private Button mDateFromButton;
    private EditText mDateFromTextView;
    private Button mDateToButton;
    private EditText mDateToTextView;
    private ArrayAdapter<ScheduleType> mScheduleTypeSpinAdapter;

    private ScheduleType mScheduleType;
    private ScheduleShift mFirstShift;

    private static final String DATE_FROM_FRAGMENT = "date_from_fragment";
    private static final String DATE_TO_FRAGMENT = "date_to_fragment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mScheduleSpinner = (Spinner) findViewById(R.id.spinnerScheduleType);
        mFirstShiftSpinner = (Spinner) findViewById(R.id.spinnerFirstShift);

        mDateFromButton = (Button) findViewById(R.id.dateFromButton);
        mDateFromTextView = (EditText) findViewById(R.id.dateFromText);
        mDateToButton = (Button) findViewById(R.id.dateToButton);
        mDateToTextView = (EditText) findViewById(R.id.dateToText);

        setOnClickButtons();
        setScheduleTypeSpinner();
        setFirstShiftSpinner();

        mScheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mScheduleType = getScheduleTypes()[position];
                setFirstShiftSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFirstShiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mFirstShift = mScheduleType.getShifts().elementAt(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setScheduleTypeSpinner() {
        mScheduleSpinner.setAdapter(new ScheduleTypeAdapter(this, R.layout.spinner_item, getScheduleTypes()));
        mScheduleType = (ScheduleType) mScheduleSpinner.getSelectedItem();
    }

    private void setFirstShiftSpinner() {
        mFirstShiftSpinner.setAdapter(new ScheduleShiftAdapter(this, R.layout.spinner_item, getScheduleShifts(mScheduleType)));
        mFirstShift = (ScheduleShift) mFirstShiftSpinner.getSelectedItem();
    }

    private void setOnClickButtons() {
        mDateFromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendar(mDateFromTextView.getText().toString());
                    dialogFragment = new ScheduleDatePickerDialog(getString(R.string.choose_date_from), calendar);
                } catch (ParseException e) {
                    dialogFragment = new ScheduleDatePickerDialog(getString(R.string.choose_date_from));
                }
                dialogFragment.show(getFragmentManager(), DATE_FROM_FRAGMENT);
            }
        });

        mDateToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendar(mDateToTextView.getText().toString());
                    dialogFragment = new ScheduleDatePickerDialog(getString(R.string.choose_date_to), calendar);
                } catch (ParseException e) {
                    dialogFragment = new ScheduleDatePickerDialog(getString(R.string.choose_date_to));
                }
                dialogFragment.show(getFragmentManager(), DATE_TO_FRAGMENT);
            }
        });

    }

    private ScheduleType[] getScheduleTypes() {
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        Vector<ScheduleType> scheduleTypesVector = new Vector<>();

        try {
            scheduleTypesVector.addAll(scheduleTypeManager.scheduleTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleTypesVector.toArray(new ScheduleType[scheduleTypesVector.size()]);
    }

    private ScheduleShift[] getScheduleShifts(ScheduleType scheduleType) {
        Vector<ScheduleShift> scheduleShiftsVector = new Vector<>();

        try {
            scheduleShiftsVector.addAll(scheduleType.getShifts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleShiftsVector.toArray(new ScheduleShift[scheduleShiftsVector.size()]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(Calendar calendar, String title) {
        String dateString = CalendarUtils.calendarToViewString(calendar);
        if (title.equals(getString(R.string.choose_date_from))) {
            mDateFromTextView.setText(dateString);
        } else if (title.equals(getString(R.string.choose_date_to))) {
            mDateToTextView.setText(dateString);
        }
    }
}
