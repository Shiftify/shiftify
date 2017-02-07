package cz.cvut.fit.shiftify.schedules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;

/**
 * Created by petr on 2/7/17.
 */

public class ScheduleDeleteDialog extends DialogFragment {

    public static ScheduleDeleteDialog newInstance() {
        ScheduleDeleteDialog fragment = new ScheduleDeleteDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.schedule_delete_title);
        builder.setMessage(R.string.schedule_delete_message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long scheduleId = getArguments().getLong(ScheduleListActivity.SCHEDULE_ID);
                try {
                    new UserManager().deleteSchedule(scheduleId);
                    Log.d("TAG", "Schedule id: " + String.valueOf(scheduleId) + " was deleted.");
                    updateList();
                    new CustomSnackbar(getActivity(), R.string.schedule_after_delete).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void updateList() {
        ScheduleListActivity.ScheduleListFragment list = (ScheduleListActivity.ScheduleListFragment) getFragmentManager().findFragmentById(R.id.fragment_schedule_container);
        list.updateSchedulesList();
    }
}
