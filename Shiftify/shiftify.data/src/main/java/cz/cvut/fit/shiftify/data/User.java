package cz.cvut.fit.shiftify.data;

import android.provider.ContactsContract;

/**
 * Created by lukas on 11.11.2016.
 */

public class User {
    public User() { }
    public User(String firstName, String surname) {
        this(firstName, surname, null, null, null);
    }
    public User(String firstName, String surname, String phoneNumber) {
        this(firstName, surname, phoneNumber, null, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email) {
        this(firstName, surname, phoneNumber, email, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email, String nickname) {
        FirstName = firstName;
        Surname = surname;
        Nickname = nickname;
        PhoneNumber = phoneNumber;
        Email = email;
    }

    public Integer Id;
    public String FirstName;
    public String Surname;
    public String Nickname;
    public String PhoneNumber;
    public String Email;
}
