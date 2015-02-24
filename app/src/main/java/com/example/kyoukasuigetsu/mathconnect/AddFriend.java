package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AddFriend extends ActionBarActivity {
    private User user;
    private EditText mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        Bundle data = getIntent().getExtras();
        user = (User) data.getParcelable(LoginActivity.USER);

        mEmailView = (EditText)findViewById(R.id.emailField);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
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

    public void addClick(View view) {
        String friend = mEmailView.getText().toString();
        if(friend.isEmpty()) {
            mEmailView.setError("This field is required");
            mEmailView.requestFocus();
        }
        else {
            user.addFriend(friend);
            new EndpointsFriendTask().execute(new Pair<Context, String>(this,user.getEmail() + ";=;" + user.getFriendsString()));
        }
    }

    public void cancelClick(View view) {
        this.onBackPressed();
    }
}
