package cz.cvut.fit.shiftify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.cvut.fit.shiftify.data.UserManager;
import cz.cvut.fit.shiftify.data.User;

public class PersonEditActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText surname;
    private EditText nickname;
    private EditText phone;
    private EditText email;
    private ImageView image;
    private User u;

    // put this somewhere else
    String mCurrentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;

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
        image = (ImageView) findViewById(R.id.person_edit_image);


        UserManager userManager = new UserManager();

        long userId = getIntent().getLongExtra("userId",-1);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_item:
                personEditSave( this.findViewById(android.R.id.content) );
            case android.R.id.home:
                finish();
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


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
