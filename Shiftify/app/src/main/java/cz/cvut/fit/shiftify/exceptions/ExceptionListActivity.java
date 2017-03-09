package cz.cvut.fit.shiftify.exceptions;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.DatePickDialog;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.TimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/3/16.
 */

public class ExceptionListActivity extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener, DatePickDialog.DatePickDialogCallback {

    public static final String USER_ID = "user_id";
    public static final String EXCEPTION_SHIFT_ID = "exception_id";
    public static final String DELETE_DIALOG = "exception_delete_dialog";
    public static final int CREATE_EXCEPTION_REQUEST = 0;
    public static final int EDIT_EXCEPTION_REQUEST = 1;
    public static final String IS_WORKING = "is_working";
    public static final String DESCRIPTION = "description";
    public static final String EXCEPTION_SHIFT_DATE = "exception_shift_date";
    public static final String TIME_FROM = "time_from";
    public static final String TIME_TO = "time_to";

    private static final String SAVED_USER_ID = "saved_user_id";
    private static final String SAVED_SELECTED_DATE = "saved_selected_date";


    private FloatingActionButton mAddExceptionButton;
    private ListView mExceptionListView;
    private TextView mHeaderDateTextView;
    private ImageButton mCalendarButton;
    private ImageButton mArrayLeftButton;
    private ImageButton mArrayRightButton;

    private Long mUserId;
    private UserManager mUserManager;
    private ExceptionAdapter mExceptionAdapter;
    private LocalDate selectedDate;
    private ExceptionShift editedExceptionShift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            mUserId = savedInstanceState.getLong(SAVED_USER_ID);
            String selectedDateString = savedInstanceState.getString(SAVED_SELECTED_DATE);
            selectedDate = LocalDate.parse(selectedDateString, CalendarUtils.JODA_DATE_FORMATTER);
        }

        setContentView(R.layout.activity_exception_list);
        ToolbarUtils.setToolbar(this, R.string.exception_list);

        mCalendarButton = (ImageButton) findViewById(R.id.btn_cal);
        mArrayLeftButton = (ImageButton) findViewById(R.id.date_arrow_left);
        mArrayRightButton = (ImageButton) findViewById(R.id.date_arrow_right);
        mHeaderDateTextView = (TextView) findViewById(R.id.shift_list_header_date) ;

        mCalendarButton.setOnClickListener(this);
        mArrayLeftButton.setOnClickListener(this);
        mArrayRightButton.setOnClickListener(this);

        final Intent intent = getIntent();
        if (intent!=null){
            mUserId = intent.getLongExtra(ExceptionListActivity.USER_ID, 0);
        }
        else{
            Log.w("ExceptionListActivity: ", "No user_id was not provided by intent (intent==null)");
            finish();
        }


        mAddExceptionButton = (FloatingActionButton) findViewById(R.id.float_add_button);
        mAddExceptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateActivity();
            }
        });

        mExceptionListView = (ListView) findViewById(R.id.user_exception_list);
        mExceptionListView.setOnItemClickListener(this);

        mExceptionAdapter = new ExceptionAdapter(this, R.layout.list_item_exception);
        mExceptionListView.setAdapter(mExceptionAdapter);

        mUserManager = new UserManager();

        initHeaderDate();
    }

    private void initHeaderDate() {
        selectedDate = LocalDate.now();
        setHeaderDate();
    }

    private void setHeaderDate(){
        String selectedDateString = selectedDate.toString(CalendarUtils.JODA_DATE_FORMATTER);
        mHeaderDateTextView.setText(selectedDateString);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (selectedDate==null){
            initHeaderDate();
        }
        else{
            setHeaderDate();
        }

        updateExceptionList(selectedDate);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String selectedDateString = selectedDate.toString(CalendarUtils.JODA_DATE_FORMATTER);
        outState.putLong(SAVED_USER_ID, mUserId);
        outState.putString(SAVED_SELECTED_DATE, selectedDateString);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == CREATE_EXCEPTION_REQUEST) {
                try {
                    Schedule schedule = mUserManager.currentSchedule(mUserId);
                    LocalDate selectedDate = LocalDate.parse(data.getStringExtra(EXCEPTION_SHIFT_DATE), CalendarUtils.JODA_DATE_FORMATTER);
                    ExceptionInSchedule exceptionInSchedule =  mUserManager.exceptionInScheduleForDate(schedule.getId(),selectedDate);

                    if (exceptionInSchedule==null){
                        exceptionInSchedule = new ExceptionInSchedule(selectedDate, mUserId, schedule.getId());
                        mUserManager.addExceptionInSchedule(exceptionInSchedule);
                        exceptionInSchedule = mUserManager.exceptionInScheduleForDate(schedule.getId(), selectedDate);
                        ExceptionShift exceptionShift = constructResultExceptionShift(data);
                        exceptionInSchedule.addExceptionShift(exceptionShift);
                        mUserManager.editExceptionInSchedule(exceptionInSchedule);
                        mUserManager.addExceptionShift(exceptionInSchedule.getId(), exceptionShift);
                    }
                    else{
                        ExceptionShift exceptionShift = constructResultExceptionShift(data);
                        exceptionInSchedule.addExceptionShift(exceptionShift);
                        mUserManager.editExceptionInSchedule(exceptionInSchedule);
                        mUserManager.addExceptionShift(exceptionInSchedule.getId(), exceptionShift);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast toast = Toast.makeText(this, "Vyjimka byla pridana", Toast.LENGTH_SHORT);
                toast.show();
            } else if (requestCode == EDIT_EXCEPTION_REQUEST) {
                try {
                    ExceptionInSchedule exceptionInSchedule = mUserManager.exceptionInSchedule(editedExceptionShift.getExceptionInScheduleId());
                    String oldDate = exceptionInSchedule.getDate().toString(CalendarUtils.JODA_DATE_FORMATTER);
                    String newDate = data.getStringExtra(EXCEPTION_SHIFT_DATE);

                    if (oldDate.equals(newDate)){
                        ExceptionShift exceptionShiftToEdit = constructResultExceptionShift(data);
                        exceptionShiftToEdit.setId(editedExceptionShift.getId());
                        exceptionShiftToEdit.setExceptionInScheduleId(editedExceptionShift.getExceptionInScheduleId());
                        mUserManager.editExceptionShift(exceptionShiftToEdit);
                    }
                    else{
                        mUserManager.deleteExceptionShift(editedExceptionShift.getId());
                        Schedule schedule = mUserManager.currentSchedule(mUserId);
                        LocalDate selectedDate = LocalDate.parse(data.getStringExtra(EXCEPTION_SHIFT_DATE), CalendarUtils.JODA_DATE_FORMATTER);
                        exceptionInSchedule =  mUserManager.exceptionInScheduleForDate(schedule.getId(),selectedDate);
                        if (exceptionInSchedule==null){
                            exceptionInSchedule = new ExceptionInSchedule(selectedDate, mUserId, schedule.getId());
                            mUserManager.addExceptionInSchedule(exceptionInSchedule);
                            exceptionInSchedule = mUserManager.exceptionInScheduleForDate(schedule.getId(), selectedDate);
                            ExceptionShift exceptionShift = constructResultExceptionShift(data);
                            exceptionInSchedule.addExceptionShift(exceptionShift);
                            mUserManager.editExceptionInSchedule(exceptionInSchedule);
                            mUserManager.addExceptionShift(exceptionInSchedule.getId(), exceptionShift);
                        }
                        else{
                            ExceptionShift exceptionShift = constructResultExceptionShift(data);
                            exceptionInSchedule.addExceptionShift(exceptionShift);
                            mUserManager.editExceptionInSchedule(exceptionInSchedule);
                            mUserManager.addExceptionShift(exceptionInSchedule.getId(), exceptionShift);
                        }
                        editedExceptionShift=null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast toast = Toast.makeText(this, "Zmeny vyjimky byly ulozeny", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                throw new RuntimeException("Unexpected result code recieved from ExceptionEditActivity!");
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED){
            editedExceptionShift=null;
        }
        updateExceptionList(selectedDate);
    }

    ExceptionShift constructResultExceptionShift(Intent data){
        Boolean isWorking = data.getBooleanExtra(IS_WORKING, false);
        String description = data.getStringExtra(DESCRIPTION);
        String stringTimeFrom = data.getStringExtra(TIME_FROM);
        String stringTimeTo = data.getStringExtra(TIME_TO);

        LocalTime timeFrom = LocalTime.parse(stringTimeFrom, TimeUtils.JODA_TIME_FORMATTER);
        LocalTime timeTo = LocalTime.parse(stringTimeTo, TimeUtils.JODA_TIME_FORMATTER);

        Period duration = new Period(timeFrom, timeTo);

        return new ExceptionShift(timeFrom, duration, isWorking, description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == android.R.id.list) {
            menu.setHeaderTitle(R.string.choose_action);
            menu.add(Menu.NONE, R.string.edit, Menu.NONE, getString(R.string.edit));
            menu.add(Menu.NONE, R.string.delete, Menu.NONE, getString(R.string.delete));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.string.edit: // Edit
                startEditActivity(mExceptionAdapter.getItem(info.position));
                return true;
            case R.string.delete: // Delete
                showDeleteDialog(mExceptionAdapter.getItem(info.position));
                updateExceptionList(selectedDate);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExceptionShift exception = mExceptionAdapter.getItem(position);
        startEditActivity(exception);
    }

    private void showDeleteDialog(ExceptionShift exceptionShift) {
        DialogFragment newFragment = ExceptionDeleteDialog.newInstance();
        Bundle userBundle = new Bundle();

        userBundle.putLong(EXCEPTION_SHIFT_ID, exceptionShift.getId());

        newFragment.setArguments(userBundle);
        newFragment.show(getFragmentManager(), DELETE_DIALOG);
    }

    private void startCreateActivity(){
        Intent intent = new Intent(ExceptionListActivity.this, ExceptionEditActivity.class);
        startActivityForResult(intent, CREATE_EXCEPTION_REQUEST);
    }

    private void startEditActivity(ExceptionShift exceptionShift) {
        editedExceptionShift = new ExceptionShift(exceptionShift);
        String exceptionDate=null;
        try {
            ExceptionInSchedule exceptionInSchedule = mUserManager.exceptionInSchedule(exceptionShift.getExceptionInScheduleId());
            exceptionDate = exceptionInSchedule.getDate().toString(CalendarUtils.JODA_DATE_FORMATTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, ExceptionEditActivity.class);
        intent.putExtra(EXCEPTION_SHIFT_ID, exceptionShift.getId());
        intent.putExtra(USER_ID, mUserId);
        intent.putExtra(EXCEPTION_SHIFT_DATE, exceptionDate);
        startActivityForResult(intent, EDIT_EXCEPTION_REQUEST);
    }

    public void updateExceptionList(LocalDate date) {
        List<ExceptionShift> exceptionNewList = new ArrayList<>(mUserManager.getUserExceptionShiftsForDate(mUserId, date));
        mExceptionAdapter.setExceptionShifts(exceptionNewList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cal:
                //creating the new dialog fragment
                DialogFragment newFragment = new DatePickDialog();
                Bundle selectedDateBundle = new Bundle();
                //preparing the bundle for creating datePicker with selected date
                selectedDateBundle.putString(DatePickDialog.SELECTED_DATE, selectedDate.toString(CalendarUtils.JODA_DATE_FORMATTER));

                //passing the bundle to the fragment
                newFragment.setArguments(selectedDateBundle);
                //showing the datePicker
                newFragment.show(getFragmentManager(), DatePickDialog.DATE_PICKER_TAG);
                break;
            case R.id.date_arrow_left:
                // date --
                selectedDate = selectedDate.minusDays(1);
                setHeaderDate();
                updateExceptionList(selectedDate);
                break;
            case R.id.date_arrow_right:
                // date ++
                selectedDate = selectedDate.plusDays(1);
                setHeaderDate();
                updateExceptionList(selectedDate);
                break;
        }
    }

    @Override
    public void onDateSet(LocalDate pickedDate) {
        selectedDate = new LocalDate(pickedDate);
        setHeaderDate();
        updateExceptionList(selectedDate);
    }
}
