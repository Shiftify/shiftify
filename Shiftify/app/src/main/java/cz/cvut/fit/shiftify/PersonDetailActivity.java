package cz.cvut.fit.shiftify;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.exceptions.ExceptionListActivity;
import cz.cvut.fit.shiftify.schedules.ScheduleListActivity;


public class PersonDetailActivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";

    TextView fullname;
    TextView numberView;
    TextView emailView;
    Button scheduleButton;
    User u;
    private UserManager mUserManager;

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
        long userId = i.getLongExtra("userId", -1);

        mUserManager = new UserManager();

        if (userId != -1) {
            try {
                u = mUserManager.user(userId);
            } catch (Exception e) {
                System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
                this.finish();
            }
        } else {
            System.err.println("Nepodarilo se nacist ID uzivatele pro detail.");
            this.finish();
        }


        emailView = (TextView) findViewById(R.id.person_detail_email);
        numberView = (TextView) findViewById(R.id.person_detail_phone);
        fullname = (TextView) findViewById(R.id.person_detail_fullname);
        scheduleButton = (Button) findViewById(R.id.person_detail_schedule_button);

        fullname.setText(u.getFullNameWithNick());
        if (u.getEmail() != null) {
            emailView.setText(u.getEmail().toString());
        }
        if (u.getPhoneNumber() != null) {
            numberView.setText(u.getPhoneNumber().toString());
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_person_detail, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        switch (id) {

            case android.R.id.home:
                this.finish();
                break;
            case R.id.person_edit:
                intent = new Intent(this, PersonEditActivity.class);
                intent.putExtra("userId", u.getId());
                startActivity(intent);
                break;

            case R.id.person_delete:
                showDialog();
                finish();
                break;
            case R.id.exception_list:
                intent = new Intent(this, ExceptionListActivity.class);
                intent.putExtra(USER_ID, u.getId());
                startActivity(intent);
                break;

            case R.id.schedule_list:
                intent = new Intent(this, ScheduleListActivity.class);
                intent.putExtra(USER_ID, u.getId());
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {

        DialogFragment newFragment = PersonDeleteDialogFragment.newInstance();
        Bundle userBundle = new Bundle();
        userBundle.putLong("userId", u.getId());
        newFragment.setArguments(userBundle);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void showDataNotSetWarning(View view) {

        Snackbar snack;
        snack = Snackbar.make(view, "Akce nelze dokoncit, nejprve vyplnte prislusne pole v detailech uzivatele.", Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#DD3A83FF"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snack.show();
    }

    public void sendSMS(View view) {

        String phoneNumber = u.getPhoneNumber();

        if (phoneNumber == null || phoneNumber == "") {

            showDataNotSetWarning(view);
            return;
        }

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + phoneNumber));
        startActivity(sendIntent);

    }

    public void callPerson(View view) {

        String phoneNumber = u.getPhoneNumber();

        if (phoneNumber == null || phoneNumber == "") {

            showDataNotSetWarning(view);
            return;
        }

        Intent sendIntent = new Intent(Intent.ACTION_DIAL);
        sendIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(sendIntent);

    }

    public void sendEmail(View view) {

        String emailAddress = u.getEmail();

        if (emailAddress == null || emailAddress == "") {

            showDataNotSetWarning(view);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void showScheduleShifts(View view) {

        Intent intent = new Intent(this, PersonShiftsActivity.class);
        intent.putExtra("userId", u.getId());
        startActivity(intent);
    }

    public void showExceptionList(View view) {

        Intent intent = new Intent(this, ExceptionListActivity.class);
        intent.putExtra(USER_ID, u.getId());
        startActivity(intent);
    }

    public void showScheduleTypeList(View view) {

        Intent intent = new Intent(this, ScheduleListActivity.class);
        intent.putExtra(USER_ID, u.getId());
        startActivity(intent);
    }


}
