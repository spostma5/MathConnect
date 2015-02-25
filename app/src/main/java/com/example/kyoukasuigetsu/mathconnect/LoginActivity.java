package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends ActionBarActivity {

    public static final String EMAIL = "EMAIL";
    public static final String USER = "USER";
    public AutoCompleteTextView mEmailView;
    public static EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
       //DO NOTHING
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

    public void signIn(View view) {
        mEmailView = (AutoCompleteTextView)findViewById(R.id.email);
        mPasswordView = (EditText)findViewById(R.id.password);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(email.isEmpty()) {
            mEmailView.setError("This field is required");
            mEmailView.requestFocus();
        }
        else if(password.isEmpty()) {
            mPasswordView.setError("This field is required");
            mPasswordView.requestFocus();
        }
        else {
            String cipherPass = toSHA1(password.getBytes());

            new EndpointsAsyncTask().execute(new Pair<Context, String>(LoginActivity.this, email + ";=;" + cipherPass));
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this,RegisterDialog.class);
        startActivity(intent);
    }

    public static String toSHA1(byte[] plaintext) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return byteArrayToHexString(md.digest(plaintext));
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
}
