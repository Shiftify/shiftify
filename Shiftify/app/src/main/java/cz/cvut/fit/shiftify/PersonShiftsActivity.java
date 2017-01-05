package cz.cvut.fit.shiftify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.managers.UserManager;

public class PersonShiftsActivity extends AppCompatActivity {

    User u;
    TextView fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_shifts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //allows for back arrow in toolbar to be created
        setSupportActionBar(toolbar);

        // creates back arrow in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();
        long userId = i.getLongExtra("userId", -1);

        UserManager userManager = new UserManager();

        if (userId != -1) {
            try {
                u = userManager.user(userId);
            } catch (Exception e) {
                System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
                this.finish();
            }
        } else {
            System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
            this.finish();
        }

        fullname = (TextView) findViewById(R.id.person_shifts_fullname);
        fullname.setText(u.getFirstName() + (u.getNickname() == null ? "" : " \"" + u.getNickname() + "\"") + " " + u.getSurname());




        /*nacteni sichet pracovnika a zobrazeni - zjistit jak se to dela pres UserManager
        * + vytvoreni noveho Custom adapteru pro tento list*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
