package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class FriendsActivity extends ActionBarActivity {
    private User user;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Bundle data = getIntent().getExtras();
        user = (User) data.getParcelable(LoginActivity.USER);

        mListView = (ListView)findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                user.getFriends() );

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new EndpointsConnectToRoomTask().execute(new Pair<Context, String>(FriendsActivity.this, user.getEmail() + ";=;" + user.getFriends().get(position)));
            }
        });

        mListView.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomePage.class);
        intent.putExtra(LoginActivity.USER,user);
        this.startActivity(intent);
    }

    public void addFriend(View view) {
        Intent intent = new Intent(this,AddFriend.class);
        intent.putExtra(LoginActivity.USER,user);
        startActivity(intent);
    }

    public void startConnect(View view) {
        new EndpointsConnectTask().execute(new Pair<Context, String>(this, user.getEmail() + ";=;" + user.getFriendsString()));
    }
}
