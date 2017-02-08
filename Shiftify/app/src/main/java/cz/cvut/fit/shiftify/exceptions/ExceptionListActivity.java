package cz.cvut.fit.shiftify.exceptions;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import cz.cvut.fit.shiftify.data.models.ExceptionShift;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.schedules.ScheduleEditActivity;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

/**
 * Created by petr on 12/3/16.
 */

public class ExceptionListActivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";
    public static final String EXCEPTION_ID = "exception_id";
    public static final String TAG = "EXCEPTION_LIST_ACTIVITY";
    public static final String DELETE_DIALOG = "exception_delete_dialog";
    private User mUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceptions_list);
        ToolbarUtils.setToolbar(this);
        UserManager userManager = new UserManager();

        Intent intent = getIntent();
        long userId = intent.getLongExtra(PersonDetailActivity.USER_ID, 0);
        mUser = userManager.user(userId);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_exception_container, new ExceptionListFragment())
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

        private static final int CREATE_EXCEPTION_REQUEST = 0;
        private static final int EDIT_EXCEPTION_REQUEST = 1;
        private ExceptionAdapter mExceptionAdapter;
        private User mUser;
        private UserManager mUserManager;
        private FloatingActionButton mAddFloatingButton;


        public ExceptionListFragment() {
            mUserManager = new UserManager();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_default_list, container, false);
            mAddFloatingButton = (FloatingActionButton) view.findViewById(R.id.float_add_button);
            mAddFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ExceptionEditActivity.class);
                    intent.putExtra(USER_ID, mUser.getId());
                    startActivityForResult(intent, CREATE_EXCEPTION_REQUEST);
                }
            });
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mUser = ((ExceptionListActivity) getActivity()).mUser;
            mExceptionAdapter = new ExceptionAdapter(getActivity(), R.layout.list_item_exception);
            setListAdapter(mExceptionAdapter);
            updateExceptionList();
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            ExceptionShift exception = mExceptionAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), ExceptionEditActivity.class);
            intent.putExtra(EXCEPTION_ID, exception.getId());
            intent.putExtra(USER_ID, mUser.getId());
            startActivityForResult(intent, EDIT_EXCEPTION_REQUEST);
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
                    startEditActivity(mExceptionAdapter.getItem(info.position));
                    return true;
                case R.string.delete: // Delete
                    showDeleteDialog(mExceptionAdapter.getItem(info.position));
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        private void startEditActivity(ExceptionShift exceptionShift) {
            Intent intent = new Intent(getActivity(), ScheduleEditActivity.class);
            intent.putExtra(EXCEPTION_ID, exceptionShift.getId());
            intent.putExtra(USER_ID, mUser.getId());
            startActivityForResult(intent, EDIT_EXCEPTION_REQUEST);
        }

        void showDeleteDialog(ExceptionShift exceptionShift) {
            DialogFragment newFragment = ExceptionDeleteDialog.newInstance();
            Bundle userBundle = new Bundle();
            userBundle.putLong(EXCEPTION_ID, exceptionShift.getId());
            newFragment.setArguments(userBundle);
            newFragment.show(getFragmentManager(), DELETE_DIALOG);
        }


        public void updateExceptionList() {
            List<ExceptionShift> exceptionNewList = new ArrayList<>(mUserManager.getAllExceptionShifts(mUser.getId()));
            mExceptionAdapter.setExceptionShifts(exceptionNewList);
        }
    }
}
