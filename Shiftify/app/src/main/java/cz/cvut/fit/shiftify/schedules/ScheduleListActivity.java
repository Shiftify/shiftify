package cz.cvut.fit.shiftify.schedules;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.PersonDetailActivity;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.Schedule;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleListActivity extends AppCompatActivity {

    public static final String SCHEDULE_ID = "schedule_id";
    public static final String USER_ID = "user_id";
    public static final String DELETE_DIALOG = "schedule_delete_dialog";
    public static final int CREATE_SCHEDULE_REQUEST = 0;
    public static final int EDIT_SCHEDULE_REQUEST = 1;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules_list);

        ToolbarUtils.setToolbar(this);

        Intent intent = getIntent();
        long userId = intent.getLongExtra(PersonDetailActivity.USER_ID, 0);
        UserManager userManager = new UserManager();
        try {
            mUser = userManager.user(userId);
        } catch (Exception e) {
            mUser = null;
            finish();
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_schedule_container, new ScheduleListFragment())
                    .commit();
        }
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


    public static class ScheduleListFragment extends ListFragment {

        private ScheduleAdapter mScheduleAdapter;
        private FloatingActionButton mAddFloatingButton;

        public ScheduleListFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_default_list, container, false);
            mAddFloatingButton = (FloatingActionButton) view.findViewById(R.id.float_add_button);
            mAddFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ScheduleEditActivity.class);
                    intent.putExtra(USER_ID, getUserId());
                    startActivityForResult(intent, CREATE_SCHEDULE_REQUEST);
                }
            });
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mScheduleAdapter = new ScheduleAdapter(getActivity(), R.layout.list_item_schedule);
            setListAdapter(mScheduleAdapter);
            registerForContextMenu(getListView());
            updateSchedulesList();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (v.getId() == android.R.id.list) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
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
                    startEditActivity(mScheduleAdapter.getItem(info.position));
                    return true;
                case R.string.delete: // Delete
                    showDeleteDialog(mScheduleAdapter.getItem(info.position));
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            startEditActivity(mScheduleAdapter.getItem(position));
        }

        void startEditActivity(Schedule schedule) {
            Intent intent = new Intent(getActivity(), ScheduleEditActivity.class);
            intent.putExtra(SCHEDULE_ID, schedule.getId());
            long userId = getUserId();
            intent.putExtra(USER_ID, userId);
            startActivityForResult(intent, EDIT_SCHEDULE_REQUEST);
        }

        private long getUserId() {
            long userId = ((ScheduleListActivity) getActivity()).mUser.getId();
            Log.d("TAG", "User id: " + String.valueOf(userId));
            return userId;
        }

        public void updateSchedulesList() {
            List<Schedule> schedules = new ArrayList<>();
            try {
                schedules.addAll(new UserManager().schedules(getUserId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            mScheduleAdapter.setSchedules(schedules);
            Log.d("TAG", "Updating, current size: " + String.valueOf(schedules.size()));
        }

        void showDeleteDialog(Schedule schedule) {
            DialogFragment newFragment = ScheduleDeleteDialog.newInstance();
            Bundle userBundle = new Bundle();
            userBundle.putLong(SCHEDULE_ID, schedule.getId());
            newFragment.setArguments(userBundle);
            newFragment.show(getFragmentManager(), DELETE_DIALOG);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            int resourceId = 0;
            if (resultCode == RESULT_OK) {
                updateSchedulesList();
                switch (requestCode) {
                    case CREATE_SCHEDULE_REQUEST:
                        resourceId = R.string.schedule_after_create;
                        break;
                    case EDIT_SCHEDULE_REQUEST:
                        resourceId = R.string.schedule_after_edit;
                    default:
                        super.onActivityResult(requestCode, resultCode, data);
                }
            } else if (resultCode == RESULT_CANCELED) {
                resourceId = R.string.schedule_after_cancel;
            } else {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            new CustomSnackbar(getActivity(), resourceId).show();
        }
    }


}
