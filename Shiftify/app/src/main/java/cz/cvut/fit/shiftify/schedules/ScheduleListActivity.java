package cz.cvut.fit.shiftify.schedules;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Vector;

import cz.cvut.fit.shiftify.PersonDetailActivity;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;

/**
 * Created by Petr on 11/13/16.
 */

public class ScheduleListActivity extends AppCompatActivity {

    public static final String SCHEDULE_ID = "schedule_id";
    public static final String USER_ID = "user_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(PersonDetailActivity.USER_ID, 0);
        UserManager userManager = new UserManager();
        User user;
        try {
            user = userManager.user(userId);
        } catch (Exception e) {
            user = null;
            finish();
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentScheduleContainer, new ScheduleListFragment(user))
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
        private User mUser;

        public ScheduleListFragment(User user) {
            mUser = user;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_schedule_list, container, false);
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            UserManager userManager = new UserManager();
            Vector<Schedule> scheduleVector = new Vector<>();

            try {
                scheduleVector.addAll(userManager.schedules(mUser.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Schedule[] schedules = scheduleVector.toArray(new Schedule[scheduleVector.size()]);
            mScheduleAdapter = new ScheduleAdapter(getActivity(), R.layout.list_item_schedule, schedules);
            setListAdapter(mScheduleAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Intent intent = new Intent(getActivity(), ScheduleEditActivity.class);
            intent.putExtra(SCHEDULE_ID, (int) id);
            intent.putExtra(USER_ID, mUser.getId());
            startActivity(intent);
        }
    }
}
