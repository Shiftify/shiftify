package cz.cvut.fit.shiftify.exceptions;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.TimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/3/16.
 */

public class ExceptionListActivity extends AppCompatActivity implements ListView.OnItemClickListener, ListView.OnItemLongClickListener, ExceptionDeleteDialog.onPositiveButtonDeleteDialogListener {

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
    public static final String SELECTED_DATE = "selected_date";

    private static final String SAVED_USER_ID = "saved_user_id";

    private FloatingActionButton mAddExceptionButton;
    private ListView mExceptionListView;
    private TextView mPersonFullNameHeader;

    private Long mUserId;
    private UserManager mUserManager;
    private ExceptionAdapter mExceptionAdapter;
    private ExceptionShift editedExceptionShift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            mUserId = savedInstanceState.getLong(SAVED_USER_ID);
        }

        mUserManager = new UserManager();

        setContentView(R.layout.activity_person_exception_list);
        ToolbarUtils.setToolbar(this, R.string.exception_list);

        final Intent intent = getIntent();
        if (intent!=null){
            mUserId = intent.getLongExtra(ExceptionListActivity.USER_ID, 0);
        }
        else{
            Log.w("ExceptionListActivity: ", "No user_id was not provided by intent (intent==null)");
            finish();
        }

        mAddExceptionButton = (FloatingActionButton) findViewById(R.id.add_exception_float_button);
        mAddExceptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateActivity();
            }
        });

        mPersonFullNameHeader = (TextView) findViewById(R.id.person_exceptions_fullname);
        User user = mUserManager.user(mUserId);
        mPersonFullNameHeader.setText(user.getFullNameWithNick());


        mExceptionListView = (ListView) findViewById(R.id.exception_list_view);
        mExceptionListView.setOnItemClickListener(this);
        mExceptionListView.setOnItemLongClickListener(this);

        mExceptionAdapter = new ExceptionAdapter(this, R.layout.person_exception_single);
        mExceptionListView.setAdapter(mExceptionAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateExceptionList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateExceptionList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(SAVED_USER_ID, mUserId);
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
                new CustomSnackbar(this, R.string.exception_after_create).show();
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
                new CustomSnackbar(this, R.string.exception_after_edit).show();
            } else {
                throw new RuntimeException("Unexpected result code recieved from ExceptionEditActivity!");
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED){
            editedExceptionShift=null;
        }
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
                updateExceptionList();
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
        intent.putExtra(SELECTED_DATE, LocalDate.now().toString(CalendarUtils.JODA_DATE_FORMATTER));
        startActivityForResult(intent, CREATE_EXCEPTION_REQUEST);
    }

    private void startEditActivity(ExceptionShift exceptionShift) {
        editedExceptionShift = new ExceptionShift(exceptionShift);

        Intent intent = new Intent(this, ExceptionEditActivity.class);
        intent.putExtra(EXCEPTION_SHIFT_ID, exceptionShift.getId());
        intent.putExtra(USER_ID, mUserId);
        intent.putExtra(EXCEPTION_SHIFT_DATE, exceptionShift.getDate().toString(CalendarUtils.JODA_DATE_FORMATTER));
        startActivityForResult(intent, EDIT_EXCEPTION_REQUEST);
    }

    private void updateExceptionList() {
        List<ExceptionShift> exceptionShifts = new ArrayList<>(mUserManager.getUserExceptionShifts(mUserId));
        mExceptionAdapter.setExceptionShifts(exceptionShifts);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ExceptionShift exceptionShift = (ExceptionShift) mExceptionListView.getItemAtPosition(position);
        showDeleteDialog(exceptionShift);
        return true;
    }

    @Override
    public void onClickPositiveButton() {
        new CustomSnackbar(this, R.string.exception_after_delete).show();
        updateExceptionList();
    }
}
