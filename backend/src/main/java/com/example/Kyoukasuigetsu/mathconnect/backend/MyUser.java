package com.example.Kyoukasuigetsu.mathconnect.backend;

/**
 * Created by Kyoukasuigetsu on 22/02/2015.
 */
public class MyUser {

    private String username,password, email;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getUser() {
        return username + ";=;" + password;
    }
    public String getEmail()    {return email; }

    public void setUsername(String newUsername) { username = newUsername; }
    public void setPassword(String newPassword) { password = newPassword; }
    public void setEmail   (String newEmail)    { email    = newEmail   ; }
}
