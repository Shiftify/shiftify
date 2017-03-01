package cz.cvut.fit.shiftify.helpers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;

/**
 * Created by Vojta on 09.02.2017.
 */

public class Validator {

    public static boolean emailValid(String email){

        if( email != null && !email.isEmpty()){
            Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            return pattern.matcher(email).matches();
        }
        else{
            return false;
        }
    }

    public static boolean phoneValid(String phone){

        if( phone != null && !phone.isEmpty()){
            Pattern pattern=Pattern.compile("^\\+(?:[0-9]●?){6,14}[0-9]$");
            return pattern.matcher(phone).matches();
        }
        else{
            return false;
        }
    }

    public static boolean duplicitUser(User user){

        String nick;
        UserManager userManager = new UserManager();
        ArrayList<User> users = (ArrayList<User>) userManager.allUsers();

        for (User u: users) {

                if(u.getId() == user.getId())   //  kvuli PersonEdit - hazelo to duplicitu pri editaci protoze nacital zrovna editovaneho mezi ostatnimi z db
                    continue;
                nick = (user.getNickname().isEmpty() ? " " : " \"" + user.getNickname().trim() + "\" ");
                if( u.getFullNameWithNick().equals(user.getFirstName().trim() + nick + user.getSurname().trim()) ){
                    return true;
                }
        }

        return false;
    }



}
