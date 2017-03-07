package cz.cvut.fit.shiftify.exceptions;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.PersonDetailActivity;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.schedules.ScheduleEditActivity;
import cz.cvut.fit.shiftify.utils.CalendarUtils;
import cz.cvut.fit.shiftify.utils.TimeUtils;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/3/16.
 */

public class ExceptionListActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    public static final String USER_ID = "user_id";
    public static final String EXCEPTION_ID = "exception_id";
    public static final String DELETE_DIALOG = "exception_delete_dialog";
    public static final int CREATE_EXCEPTION_REQUEST = 0;
    public static final int EDIT_EXCEPTION_REQUEST = 1;
    public static final String IS_WORKING = "is_working";
    public static final String DESCRIPTION = "description";
    public static final String FROM = "from";
    public static final String TIME_FROM = "time_from";
    public static final String TIME_TO = "time_to";

    /*private static final String S_EXCEPTION_ADAPTER = "exception_adapter";
    private static final String S_FLOATING_ACTION_BUTTON = "floating_action_button";
    private static final String S_USER_ID = "user_id";
    private static final String S_USER_MANAGER  = "user_manager";*/

    private ExceptionAdapter mExceptionAdapter;
    private FloatingActionButton mAddExceptionButton;
    private ListView mExceptionListView;
    private Long mUserId;
    private UserManager mUserManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_list);
        ToolbarUtils.setToolbar(this);

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
                Intent intent = new Intent(ExceptionListActivity.this, ExceptionEditActivity.class);
                intent.putExtra(USER_ID, mUserId);
                startActivityForResult(intent, CREATE_EXCEPTION_REQUEST);
            }
        });

        mExceptionListView = (ListView) findViewById(R.id.user_exception_list);
        mExceptionListView.setOnItemClickListener(this);

        mExceptionAdapter = new ExceptionAdapter(this, R.layout.list_item_exception);
        mExceptionListView.setAdapter(mExceptionAdapter);

        mUserManager = new UserManager();

        updateExceptionList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == CREATE_EXCEPTION_REQUEST) {
                try {
                    Schedule schedule = mUserManager.currentSchedule(mUserId);
                    LocalDate selectedDate = LocalDate.parse(data.getStringExtra(FROM), CalendarUtils.JODA_DATE_FORMATTER);
                    ExceptionInSchedule exceptionInSchedule =  mUserManager.exceptionInScheduleForDate(schedule.getId(),selectedDate);

                    if (exceptionInSchedule==null){
                        exceptionInSchedule = new ExceptionInSchedule(selectedDate, mUserId, schedule.getId());
                        mUserManager.addExceptionInSchedule(exceptionInSchedule);
                        exceptionInSchedule = mUserManager.exceptionInScheduleForDate(schedule.getId(), selectedDate);
                        ExceptionShift exceptionShift = constructResultExceptionShift(data, exceptionInSchedule.getId());
                        //TODO add exceptionShift to the database
                    }
                    else{
                        ExceptionShift exceptionShift = constructResultExceptionShift(data, exceptionInSchedule.getId());
                        //TODO add exceptionShift to the database
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == EDIT_EXCEPTION_REQUEST) {

            } else {
                throw new RuntimeException("Unexpected result code recieved from ExceptionEditActivity!");
            }
            updateExceptionList();
        }
    }

    ExceptionShift constructResultExceptionShift(Intent data, Long exceptionInScheduleId){
        Boolean isWorking = data.getBooleanExtra(IS_WORKING, false);
        String description = data.getStringExtra(DESCRIPTION);
        String stringTimeFrom = data.getStringExtra(TIME_FROM);
        String stringTimeTo = data.getStringExtra(TIME_TO);

        LocalTime timeFrom = LocalTime.parse(stringTimeFrom, TimeUtils.JODA_TIME_FORMATTER);
        LocalTime timeTo = LocalTime.parse(stringTimeTo, TimeUtils.JODA_TIME_FORMATTER);

        Period duration = new Period(timeFrom, timeTo);

        ExceptionShift exceptionShift = new ExceptionShift();
        exceptionShift.setFrom(timeFrom);
        exceptionShift.setDuration(duration);
        exceptionShift.setExceptionInScheduleId(exceptionInScheduleId);
        exceptionShift.setIsWorking(isWorking);
        exceptionShift.setDescription(description);

        return  exceptionShift;
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
        userBundle.putLong(EXCEPTION_ID, exceptionShift.getId());
        newFragment.setArguments(userBundle);
        newFragment.show(getFragmentManager(), DELETE_DIALOG);
    }

    private void startEditActivity(ExceptionShift exceptionShift) {
        Intent intent = new Intent(this, ScheduleEditActivity.class);
        intent.putExtra(EXCEPTION_ID, exceptionShift.getId());
        intent.putExtra(USER_ID, mUserId);
        startActivityForResult(intent, EDIT_EXCEPTION_REQUEST);
    }

    public void updateExceptionList() {
        List<ExceptionShift> exceptionNewList = new ArrayList<>(mUserManager.getAllExceptionShifts(mUserId));
        mExceptionAdapter.setExceptionShifts(exceptionNewList);
    }
}
