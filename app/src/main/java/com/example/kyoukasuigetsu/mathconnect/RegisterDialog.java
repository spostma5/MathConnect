package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;


public class RegisterDialog extends ActionBarActivity {

    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_dialog);

        //Initialize fields and set focus
        mEmail = (AutoCompleteTextView) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordConfirm = (EditText) findViewById(R.id.passwordConfirm);
    }

    public void cancelClick(View view) {
        this.onBackPressed();
    }

    public void registerClick(View view) {
        //Reset errors'
        mEmail.setError(null);
        mPassword.setError(null);
        mPasswordConfirm.setError(null);

        boolean cancel = false;
        View focusView = null;

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        //Check for a valid password, if the user entered one
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        //Check for matching passwords
        if(!password.equals(passwordConfirm)) {
            mPasswordConfirm.setError("The passwords do not match");
            focusView = mPasswordConfirm;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            new EndpointsRegisterTask().execute(new Pair<Context, String>(this, email + ";=;" + password));
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                //do nothing
            }
            finish();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_dialog, menu);
        return true;
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
}
