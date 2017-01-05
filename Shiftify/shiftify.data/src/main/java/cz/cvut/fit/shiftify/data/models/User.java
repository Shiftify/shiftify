package cz.cvut.fit.shiftify.data.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * Created by lukas on 11.11.2016.
 */

@Entity(nameInDb = "users", generateConstructors = false, generateGettersSetters = false)
public class User {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;


    private String nickname;
    private String phoneNumber;
    private String email;
    private String picturePath;

    public User() {
    }

    public User(@NotNull String firstName, @NotNull String surname) {
        this(firstName, surname, null, null, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber) {
        this(firstName, surname, phoneNumber, null, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email) {
        this(firstName, surname, phoneNumber, email, null, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname) {
        this(firstName, surname, phoneNumber, email, nickname, null);
    }

    public User(@NotNull String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        super();
        setFirstName(firstName);
        setSurname(surname);
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPicturePath(picturePath);
    }

    public User(Long id, String firstName, @NotNull String surname, String phoneNumber, String email,
                String nickname, String picturePath) {
        setId(id);
        setFirstName(firstName);
        setSurname(surname);
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPicturePath(picturePath);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public String getFullNameNick() {
        String res = firstName + " ";
        if (nickname != null) {
            res += "\"" + nickname + "\" ";
        }
        res += surname;
        return res;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
