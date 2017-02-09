package cz.cvut.fit.shiftify;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.managers.UserManager;

public class PersonShiftsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    User u;
    UserManager userManager;
    TextView fullname;
    ListView shiftListView;
    private CustomPersonShiftsAdapter adapter;
    ArrayList<WorkDay> workDayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_shifts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        shiftListView = (ListView) findViewById(R.id.shift_list_view);


        //allows for back arrow in toolbar to be created
        setSupportActionBar(toolbar);

        // creates back arrow in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i = getIntent();
        long userId = i.getLongExtra("userId", -1);

        userManager = new UserManager();

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
        fullname.setText(u.getFullNameWithNick());


        loadShifts();


        adapter = new CustomPersonShiftsAdapter(this,workDayList);
        shiftListView.setAdapter(adapter);

    }

    private void loadShifts(){

        // NACITA ZATIM JEN SICHTY NA 30 DNI DOPREDU - FIX THIS BY IMPLEMENTING AN INFINITE SCROLLABLE LAYOUT

        GregorianCalendar to = new GregorianCalendar();
        GregorianCalendar from = new GregorianCalendar();
        to.add(Calendar.DATE,30);
        System.out.println(to.getTime().toString());

        try {
            workDayList = (ArrayList<WorkDay>) userManager.shiftsForPeriod(u.getId(),from, to);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
