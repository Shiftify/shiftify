package cz.cvut.fit.shiftify;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;

public class PersonAddActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText surname;
    private EditText nickname;
    private EditText phone;
    private EditText email;
    private ImageView image;
    private UserManager userManager;
    List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //allows for back arrow in toolbar to be created
        setSupportActionBar(toolbar);

        // creates back arrow in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firstname = (EditText) findViewById(R.id.add_text_firstname);
        surname = (EditText) findViewById(R.id.add_text_surname);
        nickname = (EditText) findViewById(R.id.add_text_nickname);
        phone = (EditText) findViewById(R.id.add_text_phone);
        email = (EditText) findViewById(R.id.add_text_email);
        image = (ImageView) findViewById(R.id.add_image);

        setMandatoryFieldErrors();

        userManager = new UserManager();
        users = userManager.allUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_item:

                if( validateUserData() )
                    personAddSave(this.findViewById(android.R.id.content));

                 break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void personAddSave(View view){

        User u = new User(
                firstname.getText().toString().trim(),
                surname.getText().toString().trim(),
                phone.getText().toString().trim(),
                email.getText().toString().trim(),
                nickname.getText().toString().trim(),
                ""                                  // PATH TO IMAGE is empty bcs image handling is not fully implemented
        );

        try {
            userManager.add(u);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            CustomSnackbar c = new CustomSnackbar(view,"Nepodařilo se uložit záznam!");
            c.show();
            return;
        }

        this.finish();
    }

    public boolean validateUserData(){

        if(firstname.getText().toString().isEmpty()) {

            firstname.setError("Pole je povinné");
            firstname.requestFocus();

            return false;
        }
        if(surname.getText().toString().isEmpty()) {

            surname.setError("Pole je povinné");
            surname.requestFocus();
            return false;
        }

        if( duplicitUser() ){
            View v = this.findViewById(android.R.id.content);
            CustomSnackbar c = new CustomSnackbar(v,"Duplicita - změnte Jméno/Příjmení, či přidejte přezdívku.");
            c.show();
            return false;
        }



        return true;
    }

    public boolean duplicitUser(){
        String nick;

        for (User u: users
             ) {
            nick = (nickname.getText().toString().isEmpty() ? " " : " \"" + nickname.getText().toString().trim() + "\" ");
            if( u.getFullNameWithNick().equals(firstname.getText().toString().trim() + nick + surname.getText().toString().trim()) ){
                return true;
            }
        }

        return false;
    }


    public void setMandatoryFieldErrors(){

        firstname.setError("Pole je povinné");
        surname.setError("Pole je povinné");
    }

}
