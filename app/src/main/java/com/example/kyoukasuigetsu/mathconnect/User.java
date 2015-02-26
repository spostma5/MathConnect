package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Kyoukasuigetsu on 23/02/2015.
 */
public class User implements Parcelable{
    private String username, password, email;
    private String profilePic;
    private ArrayList<String> friends;
    private Context context;

    public User(Context c) {
        username = "INVALID";
        password = "INVALID";
        email = "INVALID";
        context = c;
        profilePic = "";
        friends = new ArrayList<String>();
    }

    public User(String str, Context c) {
        username = str.split(";=;")[0];
        password = str.split(";=;")[1];
        email = str.split(";=;")[2];
        context = c;

        try {
            profilePic = str.split(";=;")[4];
        } catch(Exception e) {
            profilePic = "";
        }

        friends = new ArrayList<String>();
        try {
            String frStr = str.split(";=;")[3];
            for(int i = 0; i < frStr.split("&&&").length;i++) {
                friends.add(frStr.split("&&&")[i]);
            }
        } catch (Exception e) {
            //DO NOTHING
        }
    }

    public User(Parcel in) {
        String[] data = new String[5];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        this.email = data[2];

        try {
            if(data[3].split("=;=")[0].isEmpty()) {
                data[3] = data[3].substring(3);
            }
        } catch(Exception e) {
            //DO NOTHING
        }
        friends = new ArrayList<String>();
        for(int i=0; i<data[3].split("&&&").length; i++) {
            friends.add(data[3].split("&&&")[i]);
        }

        try {
            this.profilePic = data[4];
        } catch(Exception e) {
            this.profilePic = "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String friendString = "";

        for(int i=0; i<friends.size();i++) {
            friendString+=friends.get(i) + "&&&";

        }

        dest.writeStringArray(new String[] {this.username,
                                            this.password,
                                            this.email,
                                            friendString,
                                            this.profilePic});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setUsername(String newUsername) {
        username = newUsername;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public String getUsername()     { return username; }
    public String getPassword()     { return password; }
    public String getEmail()        { return email;    }
    public String getProfilePic()   { return profilePic; }

    public String getFriendsString() {
        String response = "";

        for(int i = 0; i < friends.size(); i++) {
                response += friends.get(i) + "&&&";
        }

        return response;
    }

    public void addFriend(String friend) {
        friends.add(friend);
    }

    public void setProfilePic(String pic) { profilePic = pic; }

    public ArrayList<String> getFriends() {
        return friends;
    }
}
