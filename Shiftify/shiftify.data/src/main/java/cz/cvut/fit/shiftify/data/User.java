package cz.cvut.fit.shiftify.data;

import android.os.Parcelable;
import android.provider.ContactsContract;

/**
 * Created by lukas on 11.11.2016.
 */

public class User{
    public User() { setNickname(""); }
    public User(String firstName, String surname) {
        this(firstName, surname, null, null, "");
    }
    public User(String firstName, String surname, String phoneNumber) {
        this(firstName, surname, phoneNumber, null, null, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email) {
        this(firstName, surname, phoneNumber, email, null, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email, String nickname) {
        this(firstName, surname, phoneNumber, email, nickname, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email, String nickname, String picturePath) {
        setFirstName(firstName);
        setSurname(surname);
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPicturePath(picturePath);
    }

    protected Integer id;
    protected String firstName;
    protected String surname;
    protected String nickname;
    protected String phoneNumber;
    protected String email;
    protected String picturePath;

    // getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getNickname() {
        return nickname == null ? "" : nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? "" : nickname;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPicturePath() {
        return picturePath;
    }
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
