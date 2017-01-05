package cz.cvut.fit.shiftify.schedules;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.PersonDetailActivity;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleListActivity extends AppCompatActivity {

    public static final String SCHEDULE_ID = "schedule_id";
    public static final String USER_ID = "user_id";
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

        private ArrayAdapter<Schedule> mScheduleAdapter;
        private FloatingActionButton mAddFloatingButton;

        public ScheduleListFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_default_list, container, false);
            mAddFloatingButton = (FloatingActionButton) view.findViewById(R.id.float_add_button);
            mAddFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ScheduleEditActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            UserManager userManager = new UserManager();
            List<Schedule> scheduleList = new ArrayList<>();

            try {
                scheduleList.addAll(userManager.schedules(((ScheduleListActivity)getActivity()).mUser.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Schedule[] schedules = scheduleList.toArray(new Schedule[scheduleList.size()]);
            mScheduleAdapter = new ScheduleAdapter(getActivity(), R.layout.list_item_schedule, schedules);
            setListAdapter(mScheduleAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Intent intent = new Intent(getActivity(), ScheduleEditActivity.class);
            Schedule schedule = mScheduleAdapter.getItem(position);
            intent.putExtra(SCHEDULE_ID, schedule.getId());
            intent.putExtra(USER_ID, ((ScheduleListActivity)getActivity()).mUser.getId());
            startActivity(intent);
        }
    }
}
