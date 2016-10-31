package cz.cvut.fit.schifttracker.data;

import android.telephony.PhoneNumberUtils;

/**
 * Created by petr on 10/31/16.
 */

public class Person {

    private String name;
    private String surname;
    private String phone;
    private String email;


    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
