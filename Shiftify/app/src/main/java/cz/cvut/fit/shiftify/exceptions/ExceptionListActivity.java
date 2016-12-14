package cz.cvut.fit.shiftify.exceptions;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cz.cvut.fit.shiftify.PersonDetailActivity;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.ExceptionNew;
import cz.cvut.fit.shiftify.data.ExceptionShift;
import cz.cvut.fit.shiftify.data.Schedule;
import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;
import cz.cvut.fit.shiftify.schedules.ScheduleAdapter;
import cz.cvut.fit.shiftify.schedules.ScheduleEditActivity;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/3/16.
 */

public class ExceptionListActivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";
    public static final String EXCEPTION_ID = "exception_id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceptions_list);

        ToolbarUtils.setToolbar(this);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(PersonDetailActivity.USER_ID, 0);
        User user = new User();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_exception_container, new ExceptionListFragment(user))
                    .commit();
        }


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

    public static class ExceptionListFragment extends ListFragment {

        private ArrayAdapter<ExceptionNew> mExceptionAdapter;
        private User mUser;
        private FloatingActionButton mAddFloatingButton;

        public ExceptionListFragment(User user) {
            mUser = user;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_default_list, container, false);

            mAddFloatingButton = (FloatingActionButton) view.findViewById(R.id.float_add_button);
            mAddFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ExceptionEditActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            UserManager userManager = new UserManager();
            List<ExceptionNew> exceptionNewList = new ArrayList<>(ExceptionNew.getExceptions());
//            try {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            ExceptionNew[] exceptions = exceptionNewList.toArray(new ExceptionNew[exceptionNewList.size()]);
            mExceptionAdapter = new ExceptionAdapter(getActivity(), R.layout.list_item_exception, exceptions);
            setListAdapter(mExceptionAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Intent intent = new Intent(getActivity(), ExceptionEditActivity.class);
            intent.putExtra(EXCEPTION_ID, (int) id);
            intent.putExtra(USER_ID, mUser.getId());
            startActivity(intent);
        }
    }
}
