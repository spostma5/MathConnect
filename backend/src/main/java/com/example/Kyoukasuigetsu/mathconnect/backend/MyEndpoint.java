/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Kyoukasuigetsu.mathconnect.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

import javax.inject.Named;

import static com.google.appengine.api.datastore.KeyFactory.*;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v2", namespace = @ApiNamespace(ownerDomain = "backend.mathconnect.Kyoukasuigetsu.example.com", ownerName = "backend.mathconnect.Kyoukasuigetsu.example.com", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "userLogin")
    public MyUser login(@Named("user") String user,@Named("pass") String pass) {
        MyUser response = new MyUser();


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,user);
        Query mQuery = new Query("user")
                .setFilter(mFilter);

        Entity mEntity = datastore.prepare(mQuery)
                .asSingleEntity();



        try {
            if(user.equals(mEntity.getProperty("email")) && pass.equals(mEntity.getProperty("password"))) {
                response.setEmail(user);
                response.setPassword(pass);
                response.setUsername(mEntity.getProperty("username").toString());
                response.setFriends(mEntity.getProperty("friends").toString());
            }
            else if(user.equals(mEntity.getProperty("email"))) {
                response.setUsername("VALID");
                response.setPassword("INVALID");
            }
            else {
                response.setUsername("INVALID");
                response.setPassword("VALID");
            }
        } catch (NullPointerException e) {
            response.setUsername("INVALID");
            response.setPassword("VALID");
        }

        return response;
    }

    @ApiMethod(name = "userRegister")
    public MyUser register(@Named("user") String user,@Named("pass") String pass) {
        MyUser response = new MyUser();
        response.setUsername(user);
        response.setEmail(user.split("@")[0]);
        response.setPassword(pass);


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key newKey = createKey("user", user);
        Entity newUser = new Entity(newKey);
            newUser.setProperty("email",user);
            newUser.setProperty("username",user.split("@")[0]);
            newUser.setProperty("password",pass);
            newUser.setProperty("friends","null");
            newUser.setProperty("profile_pic","null");

        datastore.put(newUser);

        return response;
    }

    @ApiMethod(name = "userPassChange")
    public MyUser passChange(@Named("user") String user,@Named("pass") String pass) {
        MyUser response = new MyUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,user);
        Query mQuery = new Query("user")
                .setFilter(mFilter);

        Entity mEntity = datastore.prepare(mQuery)
                .asSingleEntity();

        mEntity.setProperty("password",pass);

        datastore.put(mEntity);

        return response;
    }

    @ApiMethod(name = "addFriend")
    public MyUser addFriend(@Named("user") String user,@Named("friend") String friend) {
        MyUser response = new MyUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,user);
        Query mQuery = new Query("user")
                .setFilter(mFilter);

        Entity mEntity = datastore.prepare(mQuery)
                .asSingleEntity();

        mEntity.setProperty("friends",friend);

        datastore.put(mEntity);

        return response;
    }
}
