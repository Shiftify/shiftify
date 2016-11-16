package cz.cvut.fit.shiftify;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cz.cvut.fit.shiftify.data.UserManager;


/**
 * Created by Vojta on 15.11.2016.
 */

public class PersonDeleteDialogFragment extends DialogFragment {

    UserManager userManager;

    public static PersonDeleteDialogFragment newInstance() {
        PersonDeleteDialogFragment frag = new PersonDeleteDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.person_delete_title);
        builder.setMessage(R.string.person_delete_confirm);

        builder.setPositiveButton(R.string.dialog_CONFIRM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Bundle bundle = getArguments();
                int userId = bundle.getInt("userId");
                userManager = new UserManager();

                try {

                    userManager.delete(userId);
                    System.out.println("Smazan uzivatel s Id: " + userId);

                } catch (Exception e) {
                    System.err.println("Nepodarilo se smazat uzivatele: " + userId);
                }

                getActivity().finish();
            }
        });

        builder.setNegativeButton(R.string.dialog_CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

}
