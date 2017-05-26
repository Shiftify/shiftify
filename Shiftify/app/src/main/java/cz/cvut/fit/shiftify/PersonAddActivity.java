package cz.cvut.fit.shiftify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;
import cz.cvut.fit.shiftify.helpers.Validator;

public class PersonAddActivity extends AppCompatActivity {
    private static final String LOG_TAG = "shiftify.personAddAct";

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
                personAddSave(this.findViewById(android.R.id.content));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void personAddSave(View view) {
        User user = new User(
                firstname.getText().toString().trim(),
                surname.getText().toString().trim(),
                phone.getText().toString().trim(),
                email.getText().toString().trim(),
                nickname.getText().toString().trim());

        if (!Validator.validateUserData(user, view, LOG_TAG, email, phone)) {
            return;
        }

        try {
            userManager.add(user);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Save error", e);
            CustomSnackbar c = new CustomSnackbar(view, "Nepodařilo se uložit záznam!");
            c.show();
            return;
        }

        this.finish();
    }

}
