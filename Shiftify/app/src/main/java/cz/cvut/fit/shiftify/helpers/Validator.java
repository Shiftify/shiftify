package cz.cvut.fit.shiftify.helpers;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Map;

import cz.cvut.fit.shiftify.data.App;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.validator.EntityValidator;
import cz.cvut.fit.shiftify.data.validator.ValidatorMessage;
import cz.cvut.fit.shiftify.data.validator.ValidatorResult;
import cz.cvut.fit.shiftify.data.validator.ValidatorState;

public class Validator {

    public static boolean validateUserData(User user, View view, String logTag, EditText email, EditText phone) {
        ValidatorResult validatorResult;

        try {
            validatorResult = EntityValidator.validateWithAllMessages(user);
        } catch (ReflectiveOperationException e) {
            Log.e(logTag, "Fatal exception during validation", e);

            CustomSnackbar snackbar = new CustomSnackbar(view, "Chyba při validaci.");
            snackbar.show();

            return false;
        }

        Map<String, ValidatorMessage> msg = validatorResult.getMessages();

        ValidatorMessage mailRes = msg.get("email");
        if (mailRes != null) {
            int iconId = (mailRes.getState() == ValidatorState.WARNING) ? android.R.drawable.stat_sys_warning : android.R.drawable.stat_notify_error;
            Drawable d = App.getsContext().getDrawable(iconId);
            email.setError(mailRes.getExplanation(), d);
        }

        ValidatorMessage phoneRes = msg.get("phoneNumber");
        if (phoneRes != null) {
            int iconId = (phoneRes.getState() == ValidatorState.WARNING) ? android.R.drawable.stat_sys_warning : android.R.drawable.stat_notify_error;
            Drawable d = App.getsContext().getDrawable(iconId);
            phone.setError(phoneRes.getExplanation(), d);
        }

        UserManager userManager = new UserManager();
        User duplicit = userManager.getUser(user.getFirstName(), user.getNickname(), user.getSurname());

        if (duplicit != null && !duplicit.getId().equals(user.getId())) {
            CustomSnackbar c = new CustomSnackbar(view, "Duplicita - změnte Jméno/Příjmení, či přidejte přezdívku.");
            c.show();
            return false;
        }

        return validatorResult.getFinalState().compareTo(ValidatorState.WARNING) <= 0;
    }
}
