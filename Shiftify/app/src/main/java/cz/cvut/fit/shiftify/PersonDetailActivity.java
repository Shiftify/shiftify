package cz.cvut.fit.shiftify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;
import cz.cvut.fit.shiftify.schedules.ScheduleListActivity;


public class PersonDetailActivity extends AppCompatActivity {

    TextView fullname;
    TextView numberView;
    TextView emailView;
    Button mScheduleShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //allows for back arrow in toolbar to be created
        setSupportActionBar(toolbar);

        // creates back arrow in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        int userId = i.getIntExtra("userId", -1);

        UserManager userManager = new UserManager();
        User u = null;

        if (userId != -1) {
            try {
                u = userManager.user(userId);
            } catch (Exception e) {
                System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
            }
        } else
            System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");

        emailView = (TextView) findViewById(R.id.person_detail_email);
        numberView = (TextView) findViewById(R.id.person_detail_phone);
        fullname = (TextView) findViewById(R.id.person_detail_fullname);
        mScheduleShowButton = (Button) findViewById(R.id.button_shift_plan_person);


        fullname.setText(u.getFirstName() + (u.getNickname() == null ? "" : " " + u.getNickname()) + " " + u.getSurname());
        emailView.setText(u.getEmail().toString());
        numberView.setText(u.getPhoneNumber().toString());

        mScheduleShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonDetailActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_person_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.person_edit:
                Intent i = new Intent(this, PersonEditActivity.class);
                startActivity(i);

        }
        //switch


        return super.onOptionsItemSelected(item);
    }


}
