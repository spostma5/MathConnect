package com.example.Kyoukasuigetsu.mathconnect.backend;

/**
 * Created by Kyoukasuigetsu on 22/02/2015.
 */
public class MyUser {

    private String username,password, email, friends, picture;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getUser() {
        String toReturn = username + ";=;" + password + ";=;" + email + ";=;" + friends + ";=;" + picture;

        return toReturn;
    }
    public String getEmail()    {return email; }

    public void setUsername(String newUsername) { username = newUsername; }
    public void setPassword(String newPassword) { password = newPassword; }
    public void setEmail   (String newEmail)    { email    = newEmail   ; }
    public void setFriends(String newFriends) {
        friends = newFriends;
    }
    public void setPicture(String newPicture) { picture = newPicture; }
}
