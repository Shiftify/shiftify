package cz.cvut.fit.shiftify.main;

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

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;

/**
 * Created by petr on 12/14/16.
 */

public class ScheduleTypeListFragment extends ListFragment {

    private ArrayAdapter<ScheduleType> mScheduleTypeAdapter;
    private FloatingActionButton mAddFloatBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_list, container, false);
        mAddFloatBtn = (FloatingActionButton) view.findViewById(R.id.float_add_button);
        mAddFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeatureSnackBar();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ScheduleTypeManager typeManager = new ScheduleTypeManager();
        List<ScheduleType> types = new ArrayList<>();
        try {
            types = typeManager.scheduleTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScheduleType[] scheduleTypes = types.toArray(new ScheduleType[types.size()]);
        mScheduleTypeAdapter = new ScheduleTypeManageAdapter(getActivity(), R.layout.list_item_schedule_type, scheduleTypes);
        setListAdapter(mScheduleTypeAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showFeatureSnackBar();
    }

    private void showFeatureSnackBar(){
        new CustomSnackbar(getActivity(), R.string.feature_in_dev).show();
    }
}
