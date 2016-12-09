package cz.cvut.fit.shiftify.data;

import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.HashMap;

/**
 * Created by lukas on 11.11.2016.
 */

public class User extends DbTable {
    // Constructors
    public User() {
        TABLE_NAME = "User";
        COL_NAME_FIRSTNAME = "FirstName";
        COL_NAME_SURNAME = "Surname";
        COL_NAME_NICKNAME = "Nickname";
        COL_NAME_PHONENUMBER = "PhoneNumber";
        COL_NAME_EMAIL = "Email";
        COL_NAME_PICTUREPATH = "PicturePath";
        setupVariables();

        setNickname("");
    }
    public User(String firstName, String surname) {
        this(firstName, surname, null, null, "");
    }
    public User(String firstName, String surname, String phoneNumber) {
        this(firstName, surname, phoneNumber, null, null, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email) {
        this(firstName, surname, phoneNumber, email, null, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email,
                String nickname) {
        this(firstName, surname, phoneNumber, email, nickname, null);
    }
    public User(String firstName, String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        this();
        setFirstName(firstName);
        setSurname(surname);
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPicturePath(picturePath);
    }

    // Columns names
    protected String COL_NAME_FIRSTNAME;
    protected String COL_NAME_SURNAME;
    protected String COL_NAME_NICKNAME;
    protected String COL_NAME_PHONENUMBER;
    protected String COL_NAME_EMAIL;
    protected String COL_NAME_PICTUREPATH;

    // Overridden methods of DbTable
    protected String[] getColumnNames() {
        return new String[] { COL_NAME_FIRSTNAME, COL_NAME_SURNAME, COL_NAME_NICKNAME,
                COL_NAME_PHONENUMBER, COL_NAME_EMAIL, COL_NAME_PICTUREPATH };
    }
    protected HashMap<String, ColumnType> getColumnTypes() {
        HashMap<String, ColumnType> types = new HashMap<>();
        types.put(COL_NAME_FIRSTNAME, ColumnType.NVARCHAR100);
        types.put(COL_NAME_SURNAME, ColumnType.NVARCHAR100);
        types.put(COL_NAME_NICKNAME, ColumnType.NVARCHAR100);
        types.put(COL_NAME_PHONENUMBER, ColumnType.NVARCHAR50);
        types.put(COL_NAME_EMAIL, ColumnType.NVARCHAR255);
        types.put(COL_NAME_PICTUREPATH, ColumnType.NVARCHAR1000);
        return types;
    }
    protected HashMap<String, TableAttribute[]> getColumnAttributes() {
        HashMap<String, TableAttribute[]> attrs = new HashMap<>();
        attrs.put(COL_NAME_FIRSTNAME, new TableAttribute[] { TableAttribute.NOTNULL });
        attrs.put(COL_NAME_SURNAME, new TableAttribute[] { TableAttribute.NOTNULL });
        attrs.put(COL_NAME_NICKNAME, new TableAttribute[] { TableAttribute.NOTNULL });
        attrs.put(COL_NAME_PHONENUMBER, new TableAttribute[] { TableAttribute.UNIQUE });
        attrs.put(COL_NAME_EMAIL, new TableAttribute[] { TableAttribute.UNIQUE });
        return attrs;
    }
    protected HashMap<String, String[]> getUniqueConstraints() {
        HashMap<String, String[]> constraints = new HashMap<>();
        constraints.put("unique_User_Names", new String[] { COL_NAME_FIRSTNAME,
                COL_NAME_SURNAME, COL_NAME_NICKNAME });
        return constraints;
    }

    // Values of table row
    protected String firstName;
    protected String surname;
    protected String nickname;
    protected String phoneNumber;
    protected String email;
    protected String picturePath;

    // Getters and setters
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
        return (nickname == null || nickname.isEmpty() ? null : nickname);
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
