package cz.cvut.fit.shiftify.main;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;

/**
 * Created by petr on 12/14/16.
 */

public class ScheduleTypeListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ArrayAdapter<ScheduleType> mScheduleTypeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ScheduleTypeManager typeManager = new ScheduleTypeManager();
        List<ScheduleType> types = new ArrayList<>();
        try {
            types = typeManager.scheduleTypesAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScheduleType[] scheduleTypes = types.toArray(new ScheduleType[types.size()]);
        mScheduleTypeAdapter = new ScheduleTypeManageAdapter(getActivity(), R.layout.list_item_schedule_type, scheduleTypes);
        setListAdapter(mScheduleTypeAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
