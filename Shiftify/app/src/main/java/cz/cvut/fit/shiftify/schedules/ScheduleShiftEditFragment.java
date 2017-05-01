package cz.cvut.fit.shiftify.schedules;

import android.app.Activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.joda.time.LocalTime;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.databinding.ScheduleShiftEditFragmentBinding;
import cz.cvut.fit.shiftify.helpdialogfragments.TimeDialog;
import cz.cvut.fit.shiftify.model.ScheduleShiftViewModel;
import cz.cvut.fit.shiftify.utils.TimeUtils;

/**
 * Created by Ilia on 25-Apr-17.
 */

public class ScheduleShiftEditFragment extends Fragment implements TimeDialogForFragment.TimeDialogCallback {

    private static final int TIME_TO_FRAGMENT = 0;
    private static final int TIME_FROM_FRAGMENT = 1;
    public static final String CLICKED_DAY_ITEM = "clicked_day_item";

    private ScheduleTypeEditActivity mActivity;
    private ScheduleShiftViewModel oldShift;
    private ScheduleShiftViewModel newShift;
    private int position;

    public ScheduleShiftEditFragment() {}

    public static ScheduleShiftEditFragment newInstance(int position){
        ScheduleShiftEditFragment fragment = new ScheduleShiftEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CLICKED_DAY_ITEM, position);
        fragment.setArguments(bundle);
        return fragment;
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

        position = getArguments().getInt(CLICKED_DAY_ITEM);
        if (position == -1){
            oldShift = null;
            newShift = new ScheduleShiftViewModel();
            mActivity.setToolbarText(getString(R.string.add_schedule_shift));
        }
        else{
            oldShift = mActivity.mShifts.get(position);
            newShift = new ScheduleShiftViewModel(oldShift.name.get(), oldShift.description.get(), oldShift.from.get(), oldShift.to.get(), oldShift.dayOfCycle.get());
            mActivity.setToolbarText(oldShift.dayOfCycle.get() + getString(R.string.schedule_shift));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScheduleShiftEditFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.schedule_shift_edit_fragment,
                container,
                false);
        View view = binding.getRoot();
        binding.setShift(newShift);
        binding.setFragment(this);
        return view;
    }

    public void onTimeFromClick(View view){
        showTimeDialog(TIME_FROM_FRAGMENT, newShift.from.get());
    }

    public void onTimeToClick(View view){
        showTimeDialog(TIME_TO_FRAGMENT, newShift.to.get());
    }

    private void showTimeDialog(int type, LocalTime localTime){
        TimeDialogForFragment dialogFragment = TimeDialogForFragment.newInstance();
        Bundle args = new Bundle();
        args.putString(TimeDialogForFragment.TIME_ARG, localTime.toString(TimeUtils.JODA_TIME_FORMATTER));
        args.putInt(TimeDialogForFragment.TIME_TYPE_ARG, type);
        dialogFragment.setArguments(args);
        dialogFragment.setTargetFragment(this, type);
        dialogFragment.show(getActivity().getFragmentManager(), "time_picker");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void onDoneClicked(){
        if (position == -1){
            mActivity.mShifts.add(newShift);
        }
        else{
            oldShift.name.set(newShift.name.get());
            oldShift.description.set(newShift.description.get());
            oldShift.from.set(newShift.from.get());
            oldShift.to.set(newShift.to.get());
        }
    }

    private boolean checkConstraints(){
        int day = Integer.parseInt(newShift.dayOfCycle.get());
        if (day < 1 || day > 99){
            Toast.makeText(getActivity(), getString(R.string.wrong_day_of_cycle), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_item:
                if (!checkConstraints()) return true;
                onDoneClicked();
                getActivity().getFragmentManager().popBackStack();
                return true;
            case android.R.id.home:
                getActivity().getFragmentManager().popBackStack();
                mActivity.setToolbarText(getResources().getString(R.string.schedule_type_edit));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(LocalTime localTime, int timeType) {
        switch (timeType) {
            case TIME_FROM_FRAGMENT:
                newShift.from.set(localTime);
                break;
            case TIME_TO_FRAGMENT:
                newShift.to.set(localTime);
                break;
        }
    }
}
