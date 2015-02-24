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


public class LoginActivity extends ActionBarActivity {

    public static final String EMAIL = "EMAIL";
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

        new EndpointsAsyncTask().execute(new Pair<Context, String>(LoginActivity.this, email + ";=;" + password));
    }

    public void register(View view) {
        Intent intent = new Intent(this,RegisterDialog.class);
        startActivity(intent);
    }
}
