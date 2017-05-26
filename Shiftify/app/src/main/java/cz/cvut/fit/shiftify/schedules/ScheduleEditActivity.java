package cz.cvut.fit.shiftify.schedules;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import cz.cvut.fit.shiftify.DatePickDialog;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Utilities;
import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.helpdialogfragments.DateDialog;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

import static cz.cvut.fit.shiftify.helpdialogfragments.DateDialog.DATE_TYPE_ARG;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleEditActivity extends AppCompatActivity implements DateDialog.DateDialogCallback {

    private Spinner mScheduleTypeSpinner;
    private Spinner mFirstShiftSpinner;
    private TextView mDateFromEditText;
    private Button mDateToRemoveButton;
    private TextView mDateToEditText;

    private Schedule mSchedule;
    private UserManager mUserManager;
    private ScheduleTypeManager mScheduleTypeManager;

    private ScheduleShift mFirstShift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        mUserManager = new UserManager();
        mScheduleTypeManager = new ScheduleTypeManager();

        Intent intent = getIntent();
        long scheduleId = intent.getLongExtra(ScheduleListActivity.SCHEDULE_ID, -1);
        long userId = intent.getLongExtra(ScheduleListActivity.USER_ID, 0);

        if (scheduleId == -1) {
            ToolbarUtils.setToolbar(this, R.string.schedule_add);
        } else {
            ToolbarUtils.setToolbar(this);
        }

        initSchedule(scheduleId, userId);


        mScheduleTypeSpinner = (Spinner) findViewById(R.id.spinner_schedule_type);
        mFirstShiftSpinner = (Spinner) findViewById(R.id.spinner_first_shift);

        mDateFromEditText = (TextView) findViewById(R.id.date_from_text);
        mDateToRemoveButton = (Button) findViewById(R.id.date_to_remove_button);
        mDateToEditText = (TextView) findViewById(R.id.date_to_text);

        mScheduleTypeSpinner.setAdapter(new ScheduleTypeAdapter(this, R.layout.spinner_item, getScheduleTypes()));


        mFirstShiftSpinner.setAdapter(new ScheduleShiftAdapter(this, R.layout.spinner_item, getScheduleShifts(mSchedule.getScheduleType())));
        setDateListeners();
        fillFields();
        setSpinnerListeners();
    }

    private void initSchedule(long scheduleId, long userId) {
        if (scheduleId == -1) {
            mSchedule = new Schedule(userId, null, null, null, null);
            try {
                mSchedule.setScheduleType(mScheduleTypeManager.scheduleTypes().get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSchedule.setStartingDayOfScheduleCycle(0);
            mSchedule.setFrom(LocalDate.now());
        } else {
            try {
                mSchedule = mUserManager.schedule(scheduleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (ScheduleShift shift : mSchedule.getScheduleType().getShifts()) {
            if (shift.getDayOfScheduleCycle().equals(mSchedule.getStartingDayOfScheduleCycle())) {
                mFirstShift = shift;
                break;
            }
        }
    }

    private void setSpinnerListeners() {
        mScheduleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mSchedule.setScheduleType(getScheduleTypes()[position]);
                mFirstShiftSpinner.setAdapter(new ScheduleShiftAdapter(ScheduleEditActivity.this, R.layout.spinner_item, getScheduleShifts(mSchedule.getScheduleType())));
                mFirstShiftSpinner.setSelection(getScheduleShiftSelection());
                mFirstShift = (ScheduleShift) mFirstShiftSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFirstShiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mFirstShift = getScheduleShifts(mSchedule.getScheduleType())[position];
                mSchedule.setStartingDayOfScheduleCycle(mFirstShift.getDayOfScheduleCycle());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillFields() {
        if (mSchedule.getFrom() != null) {
            mDateFromEditText.setText(mSchedule.getFrom().toString(CalendarUtils.JODA_DATE_FORMATTER));
        }
        if (mSchedule.getTo() != null) {
            mDateToEditText.setText(mSchedule.getTo().toString(CalendarUtils.JODA_DATE_FORMATTER));
        }
        mScheduleTypeSpinner.setSelection(getScheduleTypeSelection());
        mFirstShiftSpinner.setSelection(getScheduleShiftSelection());
    }

    private void showDateDialog(String type, LocalDate date) {
        DialogFragment newDialogFragment = new DateDialog();
        Bundle selectedDataBundle = new Bundle();

        selectedDataBundle.putString(DateDialog.DATE_PICKER_DIALOG_TYPE, type);
        if (date==null){
            selectedDataBundle.putString(DateDialog.SELECTED_DATE, LocalDate.now().toString(CalendarUtils.JODA_DATE_FORMATTER));
        }
        else{
            selectedDataBundle.putString(DateDialog.SELECTED_DATE, date.toString(CalendarUtils.JODA_DATE_FORMATTER));
        }
        newDialogFragment.setArguments(selectedDataBundle);
        newDialogFragment.show(getFragmentManager(), type);
    }

    private void setDateListeners() {
        mDateFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(DateDialog.DATE_FROM_FRAGMENT, mSchedule.getFrom());
            }
        });

        mDateToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(DateDialog.DATE_TO_FRAGMENT, mSchedule.getTo());
            }
        });

        mDateToRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDateToEditText.setText(getString(R.string.date_to));
                mSchedule.setTo(null);
            }
        });
    }

    private int getScheduleTypeSelection() {
        ScheduleType[] types = getScheduleTypes();
        for (int i = 0; i < types.length; i++) {
            if (Objects.equals(types[i].getId(), mSchedule.getScheduleTypeId()))
                return i;
        }
        return 0;
    }

    private int getScheduleShiftSelection() {
        ScheduleShift[] types = getScheduleShifts(mSchedule.getScheduleType());
        for (int i = 0; i < types.length; i++) {
            if (Objects.equals(types[i].getDayOfScheduleCycle(), mSchedule.getStartingDayOfScheduleCycle()))
                return i;
        }
        return 0;
    }


    private ScheduleType[] getScheduleTypes() {
        List<ScheduleType> scheduleTypesList = new ArrayList<>();

        try {
            scheduleTypesList.addAll(mScheduleTypeManager.scheduleTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scheduleTypesList.toArray(new ScheduleType[scheduleTypesList.size()]);
    }

    private ScheduleShift[] getScheduleShifts(ScheduleType scheduleType) {
        List<ScheduleShift> scheduleShiftsList = new ArrayList<>();

        try {
            scheduleShiftsList.addAll(scheduleType.getShifts());
            Collections.sort(scheduleShiftsList, new Comparator<ScheduleShift>() {
                @Override
                public int compare(ScheduleShift o1, ScheduleShift o2) {
                    return o1.getDayOfScheduleCycle().compareTo(o2.getDayOfScheduleCycle());
                }
            });
            int cnt = 1;
            for(int i = 1 ; i <= scheduleType.getDaysOfScheduleCycle(); i++){
                if (i - 1 < scheduleShiftsList.size() &&
                        scheduleShiftsList.get(i - 1).getDayOfScheduleCycle().compareTo(i) != 0){
                    // FIXME: 01.03.2017
                    // free shift doesn't even make any sence .. if there is no shift,
                    // there shouldn't be a shift or there should be null

                    scheduleShiftsList.add(i - 1, new ScheduleShift(Integer.toString(cnt++) + ". volno", new LocalTime(0, 0), new Period(0, 0, 0, 0), i));
                } else if (i - 1 >= scheduleShiftsList.size()){
                    // FIXME: 01.03.2017
                    // free shift doesn't even make any sence .. if there is no shift,
                    // there shouldn't be a shift or there should be null

                    scheduleShiftsList.add(i - 1, new ScheduleShift(Integer.toString(cnt++) + ". volno", new LocalTime(0, 0), new Period(0, 0, 0, 0), i));
                }
            }
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
                setResult(RESULT_CANCELED);
                this.finish();
                return true;
            case R.id.done_item:
                try {
                    if (mSchedule.getId() == null) {
                        mUserManager.addSchedule(mSchedule);
                    } else {
                        mUserManager.editSchedule(mSchedule);
                    }
                    setResult(RESULT_OK);
                    finish();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                setResult(RESULT_CANCELED);
                return false;
        }
    }

    private void setDateFrom(LocalDate dateFrom) {

        mDateFromEditText.setText(dateFrom.toString(CalendarUtils.JODA_DATE_FORMATTER));
        mSchedule.setFrom(dateFrom);

        if (mSchedule.getTo() != null && dateFrom.isAfter(mSchedule.getTo())) {
            Log.d("TAG", "DateFrom is bigger then DateTo");

            setDateTo(dateFrom.plusDays(1));
        }
    }

    private void setDateTo(LocalDate dateTo) {
        mDateToEditText.setText(dateTo.toString(CalendarUtils.JODA_DATE_FORMATTER));
        mSchedule.setTo(dateTo);
    }

    @Override
    public void onDateSet(LocalDate dateTime, String datepickerType) {
        switch (datepickerType) {
            case DateDialog.DATE_FROM_FRAGMENT:
                setDateFrom(dateTime);
                break;
            case DateDialog.DATE_TO_FRAGMENT:
                setDateTo(dateTime);
                break;
        }
    }
}
