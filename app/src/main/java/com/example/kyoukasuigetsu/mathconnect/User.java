package com.example.kyoukasuigetsu.mathconnect;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Kyoukasuigetsu on 23/02/2015.
 */
public class User {
    private String username, password, email;
    private Bitmap profilePic;
    private ArrayList<String> friends;

    public User() {
        username = "INVALID";
        password = "INVALID";
        email = "INVALID";
        profilePic = BitmapFactory.decodeResource(Resources.getSystem().getSystem(),R.drawable.userblank);
        friends = new ArrayList<String>();
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail()    { return email;    }

}
