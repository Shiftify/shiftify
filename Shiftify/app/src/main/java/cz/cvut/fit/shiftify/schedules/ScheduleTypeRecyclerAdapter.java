package cz.cvut.fit.shiftify.schedules;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;

import cz.cvut.fit.shiftify.BR;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.model.ScheduleShiftViewModel;

/**
 * Created by Ilia on 24-Apr-17.
 */

public class ScheduleTypeRecyclerAdapter extends RecyclerView.Adapter<ScheduleTypeRecyclerAdapter.BindingHolder>{

    private ScheduleTypeEditFragment listener;
    private ObservableArrayList<ScheduleShiftViewModel> mShifts;

    public ScheduleTypeRecyclerAdapter(ObservableArrayList<ScheduleShiftViewModel> shifts, ScheduleTypeEditFragment listener) {
        this.mShifts = shifts;
        this.listener = listener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_shift_row, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final ScheduleShiftViewModel shift = mShifts.get(position);
        holder.getBinding().setVariable(BR.shift, shift);
        holder.getBinding().setVariable(BR.listener, listener);
        holder.getBinding().executePendingBindings();
        holder.binding.getRoot().setTag(position);
    }

    @Override
    public int getItemCount() {
        return mShifts.size();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;

        public BindingHolder(View rowView){
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }

        public ViewDataBinding getBinding(){
            return binding;
        }
    }


    //swipe to delete
    public void onItemDismiss(int position) {
        mShifts.remove(position);
        notifyItemRemoved(position);
    }
}
