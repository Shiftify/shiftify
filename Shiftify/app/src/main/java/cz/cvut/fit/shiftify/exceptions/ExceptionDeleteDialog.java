package cz.cvut.fit.shiftify.exceptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import org.joda.time.LocalDate;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;

/**
 * Created by petr on 2/7/17.
 */

public class ExceptionDeleteDialog extends DialogFragment {

    onPositiveButtonDeleteDialogListener listener;

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
                Long exceptionShiftId = getArguments().getLong(ExceptionListActivity.EXCEPTION_SHIFT_ID);
                new UserManager().deleteExceptionShift(exceptionShiftId);
                Log.d("TAG", "ExceptionShift id: " + String.valueOf(exceptionShiftId) + " was deleted.");
                listener.onClickPositiveButton();

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

    @SuppressWarnings("deprecation") //for backward compatibility, dont touch ffs. More at: https://code.google.com/p/android/issues/detail?id=183358
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ExceptionListActivity) activity;
    }

    public interface onPositiveButtonDeleteDialogListener{
        void onClickPositiveButton();
    }

    /*private void updateList(LocalDate selectedDate) {
        ExceptionListActivity activity = (ExceptionListActivity) getActivity();
        activity.updateExceptionList(selectedDate);
    }*/
}
