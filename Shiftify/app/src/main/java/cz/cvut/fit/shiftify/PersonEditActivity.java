package cz.cvut.fit.shiftify;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cz.cvut.fit.shiftify.data.UserManager;
import cz.cvut.fit.shiftify.data.User;

public class PersonEditActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText surname;
    private EditText nickname;
    private EditText phone;
    private EditText email;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //allows for back arrow in toolbar to be created
        setSupportActionBar(toolbar);

        // creates back arrow in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firstname = (EditText) findViewById(R.id.edit_text_firstname);
        surname = (EditText) findViewById(R.id.edit_text_surname);
        nickname = (EditText) findViewById(R.id.edit_text_nickname);
        phone = (EditText) findViewById(R.id.edit_text_phone);
        email = (EditText) findViewById(R.id.edit_text_email);


        UserManager userManager = new UserManager();

        int userId = getIntent().getIntExtra("userId",-1);

        if(userId == -1){
            System.err.println("Nepodarilo se nacist UserId v PersonEditActivity.");
            this.finish();
        }

        try {
            u = userManager.user(userId);
        } catch (Exception e) {
            System.err.println("Nepodarilo se nacist UserId v PersonEditActivity.");
            this.finish();
        }

        firstname.setText( u.getFirstName() );
        surname.setText( u.getSurname() );
        nickname.setText( u.getNickname() );
        phone.setText( u.getPhoneNumber() );
        email.setText( u.getEmail() );

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                this.finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    // checks whether user changed any data, if not, no write is performed
    private  boolean dataChanged(){


        if(        u.getFirstName()   != firstname.getText().toString()
                || u.getSurname()     != surname.getText().toString()
                || u.getNickname()    != nickname.getText().toString()
                || u.getPhoneNumber() != phone.getText().toString()
                || u.getEmail()       != email.getText().toString() ){

            return true;
        }
        return false;
    }

    public void personEditSave(View view){

        // save data to DB

        if(!dataChanged()){
            this.finish();
        }

        u. setFirstName( firstname.getText().toString() );
        u. setSurname( surname.getText().toString() );
        u. setNickname( nickname.getText().toString() );
        u. setPhoneNumber( phone.getText().toString() );
        u. setEmail( email.getText().toString() );


        System.out.println(u.getFirstName());
        System.out.println(u.getSurname());
        System.out.println(u.getNickname());
        System.out.println(u.getPhoneNumber());
        System.out.println(u.getEmail());

        System.out.println(firstname.getText().toString());
        System.out.println(surname.getText().toString());
        System.out.println(nickname.getText().toString());
        System.out.println(phone.getText().toString());
        System.out.println(email.getText().toString());

        this.finish();
    }

    public void personImageSelect(View view){

        // call methods for image selection

        Snackbar snack;
        snack = Snackbar.make(view, "Image selection feature coming soon!", Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#DD3A83FF"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snack.show();
    }

}
