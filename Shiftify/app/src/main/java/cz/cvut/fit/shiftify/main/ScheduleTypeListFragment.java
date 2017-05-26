package cz.cvut.fit.shiftify.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;
import cz.cvut.fit.shiftify.schedules.ScheduleTypeEditActivity;

/**
 * Created by petr on 12/14/16.
 */

public class ScheduleTypeListFragment extends ListFragment {
    public static final String SCHEDULE_TYPE_ID = "schedule_type_id";


    private ArrayAdapter<ScheduleType> mScheduleTypeAdapter;
    private FloatingActionButton mAddFloatBtn;
    private List<ScheduleType> mTypes;
    private ScheduleTypeManager mManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_list, container, false);
        mAddFloatBtn = (FloatingActionButton) view.findViewById(R.id.float_add_button);
        mAddFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleTypeEditActivity.class);
                startActivityForResult(intent, MainActivity.CREATE_SCHEDULE_TYPE_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAndUpdateTypes();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mManager = new ScheduleTypeManager();
        mTypes = new ArrayList<>();
        mScheduleTypeAdapter = new ScheduleTypeManageAdapter(getActivity(), R.layout.list_item_schedule_type, mTypes);
        setListAdapter(mScheduleTypeAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Long typeId = mScheduleTypeAdapter.getItem(position).getId();
        Intent intent = new Intent(getActivity(), ScheduleTypeEditActivity.class);
        intent.putExtra(SCHEDULE_TYPE_ID, typeId);
        startActivityForResult(intent, MainActivity.EDIT_SCHEDULE_TYPE_REQUEST);
    }

    private void fetchAndUpdateTypes(){
        try {
            mTypes.clear();
            mTypes.addAll(mManager.scheduleTypes());
        } catch (Exception e) {
            showErrorToast();
        }
        mScheduleTypeAdapter.notifyDataSetChanged();
    }

    private void showErrorToast(){
        Toast toast = Toast.makeText(getContext(), getString(R.string.error_while_fetching_schedule_types), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MainActivity.CREATE_SCHEDULE_TYPE_REQUEST:
                if (resultCode == Activity.RESULT_OK){
                    new CustomSnackbar(getActivity(), R.string.schedule_type_was_created).show();
                }
                break;
            case MainActivity.EDIT_SCHEDULE_TYPE_REQUEST:
                if (resultCode == Activity.RESULT_OK){
                    new CustomSnackbar(getActivity(), R.string.schedule_type_was_edited).show();
                }
                break;
        }
    }
}
