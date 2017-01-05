package cz.cvut.fit.shiftify.schedules;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.data.ScheduleShift;
import cz.cvut.fit.shiftify.data.ScheduleType;
import cz.cvut.fit.shiftify.data.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.helpdialogfragments.DateFromDialog;
import cz.cvut.fit.shiftify.helpdialogfragments.DateToDialog;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleEditActivity extends AppCompatActivity implements DateToDialog.DateToDialogCallback, DateFromDialog.DateFromDialogCallback {

    private Spinner mScheduleSpinner;
    private Spinner mFirstShiftSpinner;
    private TextView mDateFromEditText;
    private Button mDateToRemoveButton;
    private TextView mDateToEditText;
    private ArrayAdapter<ScheduleType> mScheduleTypeSpinAdapter;

    private Schedule mSchedule;

    private ScheduleType mScheduleType;
    private ScheduleShift mFirstShift;

    private static final String DATE_FROM_FRAGMENT = "date_from_fragment";
    private static final String DATE_TO_FRAGMENT = "date_to_fragment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        Intent intent = getIntent();
        long scheduleId = intent.getLongExtra(ScheduleListActivity.SCHEDULE_ID, -1);
        long userId = intent.getLongExtra(ScheduleListActivity.USER_ID, 0);

        if (scheduleId == -1) {
            ToolbarUtils.setToolbar(this, R.string.schedule_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }

        mScheduleSpinner = (Spinner) findViewById(R.id.spinner_schedule_type);
        mFirstShiftSpinner = (Spinner) findViewById(R.id.spinner_first_shift);

        mDateFromEditText = (TextView) findViewById(R.id.date_from_text);
        mDateToRemoveButton = (Button) findViewById(R.id.date_to_remove_button);
        mDateToEditText = (TextView) findViewById(R.id.date_to_text);

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
                mFirstShift = mScheduleType.getShifts().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fillScheduleContent();
    }

    private void fillScheduleContent() {
        if (mSchedule.getScheduleTypeId() != null || mSchedule.getStartingDayOfScheduleCycle() != null) {
////            TODO select scheduleType and firstShift in Spinner
        } else {
            mScheduleType = (ScheduleType) mScheduleSpinner.getSelectedItem();
            mFirstShift = (ScheduleShift) mFirstShiftSpinner.getSelectedItem();
        }

        if (mSchedule.getFrom() != null) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(mSchedule.getFrom());
            mDateFromEditText.setText(CalendarUtils.calendarToDateString(calendarFrom));
        }
        if (mSchedule.getTo() != null) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(mSchedule.getTo());
            mDateToEditText.setText(CalendarUtils.calendarToDateString(calendarTo));
        }
    }

    private void setDateListeners() {
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
                    Calendar calendar = CalendarUtils.getCalendarFromDate(mDateToEditText.getText().toString());
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
                mDateToEditText.setText(getString(R.string.date_to));
            }
        });
    }


    private ScheduleType[] getScheduleTypes() {
        ScheduleTypeManager scheduleTypeManager = new ScheduleTypeManager();
        List<ScheduleType> scheduleTypesList = new ArrayList<>();

        try {
            scheduleTypesList.addAll(scheduleTypeManager.scheduleTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleTypesList.toArray(new ScheduleType[scheduleTypesList.size()]);
    }

    private ScheduleShift[] getScheduleShifts(ScheduleType scheduleType) {
        List<ScheduleShift> scheduleShiftsList = new ArrayList<>();

        try {
            scheduleShiftsList.addAll(scheduleType.getShifts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleShiftsList.toArray(new ScheduleShift[scheduleShiftsList.size()]);
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
        mDateFromEditText.setText(CalendarUtils.calendarToDateString(calendar));
    }

    @Override
    public void setDateTo(Calendar calendar) {
        mDateToEditText.setText(CalendarUtils.calendarToDateString(calendar));
    }
}
