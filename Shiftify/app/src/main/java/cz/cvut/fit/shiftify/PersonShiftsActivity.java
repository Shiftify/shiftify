package cz.cvut.fit.shiftify;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

public class PersonShiftsActivity extends AppCompatActivity implements DatePickDialog.DatePickDialogCallback{

    private User user;
    private UserManager userManager;
    private CustomPersonShiftsAdapter adapter;
    private List<WorkDay> workDayList;
    private LocalDate firstWeekDay;
    private LocalDate lastWeekDay;
    private LocalDate selectedDay;

    private ListView shiftListView;
    private ImageButton weekArrowLeftButton;
    private ImageButton weekArrowRightButton;
    private ImageButton calendarButton;
    private TextView firstWeekDayView;
    private TextView lastWeekDayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_shifts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        shiftListView = (ListView) findViewById(R.id.shift_list_view);

        Intent i = getIntent();
        long userId = i.getLongExtra("userId", -1);

        userManager = new UserManager();

        if (userId != -1) {
            try {
                user = userManager.user(userId);
            } catch (Exception e) {
                System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
                this.finish();
            }
        } else {
            System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
            this.finish();
        }

        workDayList = new ArrayList<>();

        // creates back arrow in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(user.getFullNameWithNick());

        weekArrowLeftButton = (ImageButton) findViewById(R.id.week_arrow_left);
        weekArrowRightButton = (ImageButton) findViewById(R.id.week_arrow_right);
        calendarButton = (ImageButton) findViewById(R.id.calendar_button);
        firstWeekDayView = (TextView) findViewById(R.id.first_week_day);
        lastWeekDayView = (TextView) findViewById(R.id.last_week_day);

        weekArrowLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = selectedDay.minusWeeks(1);
                setHeaderWeekDays();
                loadShifts();
            }
        });

        firstWeekDayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = selectedDay.minusWeeks(1);
                setHeaderWeekDays();
                loadShifts();
            }
        });

        weekArrowRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = selectedDay.plusWeeks(1);
                setHeaderWeekDays();
                loadShifts();
            }
        });

        lastWeekDayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDay = selectedDay.plusWeeks(1);
                setHeaderWeekDays();
                loadShifts();
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
        }
        });

        selectedDay = LocalDate.now();
        setHeaderWeekDays();

        adapter = new CustomPersonShiftsAdapter(this, workDayList);
        shiftListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadShifts();
    }

    private void setHeaderWeekDays(){
        firstWeekDay = selectedDay.withDayOfWeek(DateTimeConstants.MONDAY);
        lastWeekDay = selectedDay.withDayOfWeek(DateTimeConstants.SUNDAY);

        firstWeekDayView.setText(firstWeekDay.toString(CalendarUtils.JODA_DATE_FORMATTER));
        lastWeekDayView.setText(lastWeekDay.toString(CalendarUtils.JODA_DATE_FORMATTER));
    }

    private void loadShifts(){
        try {
            workDayList.clear();
            workDayList.addAll(userManager.shiftsForPeriod(user.getId(), firstWeekDay, lastWeekDay));
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load shifts!",
                    Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void showDatePicker(){
        //creating the new dialog fragment
        DialogFragment newFragment = new DatePickDialog();
        Bundle selectedDateBundle = new Bundle();
        //preparing the bundle for creating datePicker with selected date
        selectedDateBundle.putString(DatePickDialog.SELECTED_DATE, selectedDay.toString(CalendarUtils.JODA_DATE_FORMATTER));
        //passing the bundle to the fragment
        newFragment.setArguments(selectedDateBundle);
        //showing the datePicker
        newFragment.show(getFragmentManager(), DatePickDialog.DATE_PICKER_TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(LocalDate pickedDate) {
        selectedDay = pickedDate;
        setHeaderWeekDays();
        loadShifts();
    }
}
