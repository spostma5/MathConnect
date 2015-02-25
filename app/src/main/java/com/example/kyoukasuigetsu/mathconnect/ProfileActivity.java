package com.example.kyoukasuigetsu.mathconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class ProfileActivity extends ActionBarActivity {

    private Bitmap userPic;
    private ImageView imageView;
    private User user;
    private EditText mEmailView, mUserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        Bundle data = getIntent().getExtras();
        user = (User) data.getParcelable(LoginActivity.USER);

        mEmailView = (EditText)findViewById(R.id.profile_email);
        mUserView = (EditText)findViewById(R.id.profile_username);

        mEmailView.setText(user.getEmail());
        mUserView.setText(user.getUsername());

        imageView = (ImageView) findViewById(R.id.imageView4);

        try {
             String picture = user.getProfilePic();

            byte[] decodedString = Base64.decode(picture,Base64.DEFAULT);
            Bitmap selectedImage = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

            imageView.setImageBitmap(selectedImage);
            imageView.invalidate();
        } catch(Exception e) {
            //DO NOTHING
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    public void gotoChangePass(View view) {
        Intent intent = new Intent(this, ChangePassword.class);
        intent.putExtra(LoginActivity.USER,user);
        startActivity(intent);
    }

    public void gotoChangePic(View view) {
        Intent intent = new Intent(this, ChangePicture.class);
        intent.putExtra(LoginActivity.USER,user);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onKeyDown(keycode, e);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomePage.class);
        intent.putExtra(LoginActivity.USER,user);
        this.startActivity(intent);
    }
}