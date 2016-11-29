package cz.cvut.fit.shiftify.schedules;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.data.ScheduleShift;
import cz.cvut.fit.shiftify.data.ScheduleType;
import cz.cvut.fit.shiftify.data.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleEditActivity extends AppCompatActivity implements DateToDialog.DateToDialogCallback, DateFromDialog.DateFromDialogCallback {

    private Spinner mScheduleSpinner;
    private Spinner mFirstShiftSpinner;
    private Button mDateFromRemoveButton;
    private EditText mDateFromEditText;
    private Button mDateToRemoveButton;
    private EditText mDateToEditText;
    private ArrayAdapter<ScheduleType> mScheduleTypeSpinAdapter;

    private Schedule mSchedule;

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

        mScheduleSpinner = (Spinner) findViewById(R.id.spinner_schedule_type);
        mFirstShiftSpinner = (Spinner) findViewById(R.id.spinner_first_shift);

        mDateFromRemoveButton = (Button) findViewById(R.id.date_from_remove_button);
        mDateFromEditText = (EditText) findViewById(R.id.date_from_text);
        mDateToRemoveButton = (Button) findViewById(R.id.date_to_remove_button);
        mDateToEditText = (EditText) findViewById(R.id.date_to_text);


        Intent intent = getIntent();
        final Integer scheduleId = intent.getIntExtra(ScheduleListActivity.SCHEDULE_ID, -1);
        final Integer userId = intent.getIntExtra(ScheduleListActivity.USER_ID, 0);
        if (scheduleId == -1) {
            mSchedule = new Schedule(userId, null, null, null, null);
        } else {
            UserManager userManager = new UserManager();
            try {
                mSchedule = userManager.schedule(scheduleId);
            } catch (Exception e) {
                mSchedule = new Schedule(userId, null, null, null, null);
            }
        }


        mScheduleSpinner.setAdapter(new ScheduleTypeAdapter(this, R.layout.spinner_item, getScheduleTypes()));
        mFirstShiftSpinner.setAdapter(new ScheduleShiftAdapter(this, R.layout.spinner_item, getScheduleShifts(mScheduleType)));
        setDateListeners();

        mScheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mScheduleType = getScheduleTypes()[position];
                mFirstShiftSpinner.setAdapter(new ScheduleShiftAdapter(ScheduleEditActivity.this, R.layout.spinner_item, getScheduleShifts(mScheduleType)));
                mFirstShift = (ScheduleShift) mFirstShiftSpinner.getSelectedItem();
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

        fillScheduleContent();
    }

    private void fillScheduleContent() {
        if (mSchedule.getScheduleTypeId() != 0 || mSchedule.getStartingDayOfScheduleCycle() != 0) {
//            TODO select scheduleType and firstShift in Spinner
        } else {
            mScheduleType = (ScheduleType) mScheduleSpinner.getSelectedItem();
            mFirstShift = (ScheduleShift) mFirstShiftSpinner.getSelectedItem();
        }

        if (mSchedule.getFrom() != null) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(mSchedule.getFrom());
            mDateFromEditText.setText(CalendarUtils.calendarToViewString(calendarFrom));
        }
        if (mSchedule.getTo() != null) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(mSchedule.getTo());
            mDateToEditText.setText(CalendarUtils.calendarToViewString(calendarTo));
        }
    }

    private void setDateListeners() {
        mDateFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendar(mDateFromEditText.getText().toString());
                    dialogFragment = new DateFromDialog(calendar);
                } catch (ParseException e) {
                    dialogFragment = new DateFromDialog();
                }
                dialogFragment.show(getFragmentManager(), DATE_FROM_FRAGMENT);
            }
        });

        mDateFromRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDateFromEditText.setText(getString(R.string.date_from_unselected));
            }
        });

        mDateToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment;
                try {
                    Calendar calendar = CalendarUtils.getCalendar(mDateToEditText.getText().toString());
                    dialogFragment = new DateToDialog(calendar);
                } catch (ParseException e) {
                    dialogFragment = new DateToDialog();
                }
                dialogFragment.show(getFragmentManager(), DATE_TO_FRAGMENT);
            }
        });

        mDateToRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDateToEditText.setText(getString(R.string.date_to_unselected));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.done_item:
                if (mSchedule.getId() == null) {
//                    save
                } else {
//                    update
                }
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setDateFrom(Calendar calendar) {
        mDateFromEditText.setText(CalendarUtils.calendarToViewString(calendar));
    }

    @Override
    public void setDateTo(Calendar calendar) {
        mDateToEditText.setText(CalendarUtils.calendarToViewString(calendar));
    }
}
