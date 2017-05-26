package cz.cvut.fit.shiftify.schedules;

import android.app.Activity;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.databinding.ScheduleTypeEditFragmentBinding;
import cz.cvut.fit.shiftify.model.ScheduleShiftViewModel;
import cz.cvut.fit.shiftify.model.ScheduleTypeViewModel;

/**
 * Created by Ilia on 28-Apr-17.
 */

public class ScheduleTypeEditFragment extends Fragment {

    private ScheduleTypeViewModel mScheduleType;
    private RecyclerView mRecyclerView;
    private ScheduleTypeRecyclerAdapter mAdapter;
    private ScheduleTypeEditActivity mActivity;
    private ObservableArrayList<ScheduleShiftViewModel> mShifts;

    public ScheduleTypeEditFragment() {}

    public static ScheduleTypeEditFragment newInstance(){
        return new ScheduleTypeEditFragment();
    }

    @SuppressWarnings("deprecation") //for backward compatibility, dont touch ffs. More at: https://code.google.com/p/android/issues/detail?id=183358
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mActivity = (ScheduleTypeEditActivity) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " is not ScheduleTypeEditActivity");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mScheduleType = mActivity.mScheduleType;
        mShifts = mActivity.mShifts;
        Collections.sort(mShifts, new Comparator<ScheduleShiftViewModel>() {
            @Override
            public int compare(ScheduleShiftViewModel o1, ScheduleShiftViewModel o2) {
                int o1Int = Integer.parseInt(o1.dayOfCycle.get());
                int o2Int = Integer.parseInt(o2.dayOfCycle.get());
                if (o1Int == o2Int){
                    return o1.from.get().getMillisOfDay() - o2.from.get().getMillisOfDay();
                }
                return o1Int - o2Int;
            }
        });
        mActivity.setToolbarText(getResources().getString(R.string.schedule_type_edit));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScheduleTypeEditFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.schedule_type_edit_fragment,
                container,
                false);
        View view = binding.getRoot();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.shift_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ScheduleTypeRecyclerAdapter(mShifts, this);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new ScheduleTypeEditTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        binding.setType(mScheduleType);
        binding.setFragment(this);
        return view;
    }

    public void onShiftClick(int position){
        mActivity.onEditShiftClick(position);
    }

    public void onAddShiftClick(View view){
        mActivity.onAddShiftClick();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void editScheduleType(){
        ScheduleType editedType = constructResult(mActivity.mTypeId);
        try {
            mActivity.mManager.edit(editedType);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.schedule_type_edit_error), Toast.LENGTH_SHORT);
        }
    }

    private void createScheduleType(){
        ScheduleType newType = constructResult(null);
        try {
            mActivity.mManager.add(newType);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.schedule_type_create_error), Toast.LENGTH_SHORT);
        }
    }

    private ScheduleType constructResult(Long id){
        ScheduleType newType = new ScheduleType(
                id,
                mScheduleType.name.get(),
                Integer.parseInt(mShifts.get(mShifts.size()-1).dayOfCycle.get()),
                mScheduleType.description.get()
        );
        List<ScheduleShift> newShifts = new ArrayList<>();
        for (ScheduleShiftViewModel shiftVM : mShifts) {
            Period duration = Period.fieldDifference(shiftVM.from.get(), shiftVM.to.get());
            if ((shiftVM.from.get().compareTo(shiftVM.to.get()) > 0)){
                duration = duration.plusHours(24);
            }

            newShifts.add(new ScheduleShift(
                    shiftVM.name.get(),
                    shiftVM.from.get(),
                    duration,
                    Integer.parseInt(shiftVM.dayOfCycle.get()),
                    shiftVM.description.get()
            ));
        }
        newType.setShifts(newShifts);
        return newType;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_item:
                if (mActivity.mTypeId != -1){
                    editScheduleType();
                }
                createScheduleType();
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                return true;
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
