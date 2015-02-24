package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class ChangePassword extends ActionBarActivity {
    private User user;
    EditText mCurrentPass,mNewPass,mConPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle data = getIntent().getExtras();
        user = (User) data.getParcelable(LoginActivity.USER);

        mCurrentPass = (EditText)findViewById(R.id.currentPassword);
        mNewPass = (EditText)findViewById(R.id.password);
        mConPass = (EditText)findViewById(R.id.passwordConfirm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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

    public void cancelClick(View view) {
        this.onBackPressed();
    }

    public void changePass(View view) {
        String currentPass = mCurrentPass.getText().toString();
        String newPass = mNewPass.getText().toString();
        String conPass = mConPass.getText().toString();

        String cipherPass = LoginActivity.toSHA1(newPass.getBytes());

        if(currentPass.isEmpty()) {
            resetErrors();
            mCurrentPass.setError("This field is required");
            mCurrentPass.requestFocus();
        }
        else if(newPass.isEmpty()) {
            resetErrors();
            mNewPass.setError("This field is required");
            mNewPass.requestFocus();
        }
        else if(!(LoginActivity.toSHA1(currentPass.getBytes())).equals(user.getPassword())) {
            resetErrors();
            mCurrentPass.setError("Incorrect password");
            mCurrentPass.requestFocus();
        }
        else if(!isValidPass(newPass)) {
            resetErrors();
            mNewPass.setError("Invalid password");
            mNewPass.requestFocus();
        }
        else if(!newPass.equals(conPass)) {
            resetErrors();
            mConPass.setError("Passwords do not match");
            mConPass.requestFocus();
        }
        else {
            new EndpointsPasswordTask().execute(new Pair<Context, String>(this, user.getEmail() + ";=;" + cipherPass));
        }

    }

    public boolean isValidPass(String pass) { return pass.length() > 6; }

    public void resetErrors() {
        mConPass.setError(null);
        mNewPass.setError(null);
        mCurrentPass.setError(null);
    }
}
