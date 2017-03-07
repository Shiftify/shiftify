package cz.cvut.fit.shiftify.exceptions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;
import cz.cvut.fit.shiftify.schedules.ScheduleListActivity;

/**
 * Created by petr on 2/7/17.
 */

public class ExceptionDeleteDialog extends DialogFragment {

    public static ExceptionDeleteDialog newInstance() {
        ExceptionDeleteDialog fragment = new ExceptionDeleteDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.exception_delete_title);
        builder.setMessage(R.string.exception_delete_message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long scheduleId = getArguments().getLong(ExceptionListActivity.EXCEPTION_ID);
                try {
                    new UserManager().deleteSchedule(scheduleId);
                    Log.d("TAG", "ExceptionShift id: " + String.valueOf(scheduleId) + " was deleted.");
                    updateList();
                    new CustomSnackbar(getActivity(), R.string.exception_after_delete).show();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void updateList() {
        ExceptionListActivity activity = (ExceptionListActivity) getActivity();
        activity.updateExceptionList();
    }
}
