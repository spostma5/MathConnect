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
                response.setPicture(mEntity.getProperty("profilepic").toString());
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

    @ApiMethod(name = "goHome")
    public MyUser goHome(@Named("user") String user) {
        MyUser response = new MyUser();


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,user);
        Query mQuery = new Query("user")
                .setFilter(mFilter);

        Entity mEntity = datastore.prepare(mQuery)
                .asSingleEntity();

        response.setEmail(user);
        response.setPassword(mEntity.getProperty("password").toString());
        response.setUsername(mEntity.getProperty("username").toString());
        response.setFriends(mEntity.getProperty("friends").toString());
        response.setPicture(mEntity.getProperty("profilepic").toString());

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
            newUser.setProperty("friends","");
            newUser.setProperty("profilepic","");

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

        response.setEmail(user);
        response.setPassword(pass);
        response.setUsername(mEntity.getProperty("username").toString());
        response.setFriends(mEntity.getProperty("friends").toString());
        response.setPicture(mEntity.getProperty("profilepic").toString());

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

        response.setEmail(user);
        response.setPassword(mEntity.getProperty("password").toString());
        response.setUsername(mEntity.getProperty("username").toString());
        response.setFriends(mEntity.getProperty("friends").toString());
        response.setPicture(mEntity.getProperty("profilepic").toString());

        return response;
    }

    @ApiMethod(name = "setPicture")
    public MyUser setPicture(@Named("user") String user,@Named("picture") String picture) {
        MyUser response = new MyUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,user);
        Query mQuery = new Query("user")
                .setFilter(mFilter);

        Entity mEntity = datastore.prepare(mQuery)
                .asSingleEntity();

        mEntity.setProperty("profilepic",picture);

        datastore.put(mEntity);

        response.setEmail(user);
        response.setPassword(mEntity.getProperty("password").toString());
        response.setUsername(mEntity.getProperty("username").toString());
        response.setFriends(mEntity.getProperty("friends").toString());
        response.setPicture(picture);

        return response;
    }

    @ApiMethod(name = "userCreateRoom")
    public MyRoom userCreateRoom(@Named("room") String room,@Named("friends") String friends) {
        MyRoom response = new MyRoom();
        response.setRoom(room);
        response.setFriends(friends);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key newKey = createKey("room", room);
        Entity newRoom = new Entity(newKey);
        newRoom.setProperty("room",room);
        newRoom.setProperty("friends","null");
        newRoom.setProperty("colour","null");
        newRoom.setProperty("path","null");
        newRoom.setProperty("size","null");

        datastore.put(newRoom);

        return response;
    }

    @ApiMethod(name = "userJoinRoom")
    public MyRoom userJoinRoom(@Named("room") String room,@Named("friend") String friend) {
        MyRoom response = new MyRoom();
        response.setRoom(friend);
        response.setFriends(room);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query.Filter mFilter = new Query.FilterPredicate("room", Query.FilterOperator.EQUAL,room);
        Query mQuery = new Query("room")
                .setFilter(mFilter);

        Entity mEntity = datastore.prepare(mQuery)
                .asSingleEntity();

        mEntity.setProperty("friends",friend);

        datastore.put(mEntity);

        Key newKey = createKey("room", friend);
        Entity newRoom = new Entity(newKey);
        newRoom.setProperty("room",friend);
        newRoom.setProperty("friends",room);
        newRoom.setProperty("colour","null");
        newRoom.setProperty("path","null");
        newRoom.setProperty("size","null");

        datastore.put(newRoom);


        return response;
    }

    @ApiMethod(name = "userGet")
    public MyRoom userGet(@Named("room") String room) {
        MyRoom response = new MyRoom();
        response.setRoom(room);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("room", Query.FilterOperator.EQUAL,room);
        Query mQuery = new Query("room")
                .setFilter(mFilter);

        try {
            Entity mEntity = datastore.prepare(mQuery)
                    .asSingleEntity();

            response.setFriends(mEntity.getProperty("friends").toString());
            response.setColour(mEntity.getProperty("colour").toString());
            response.setPath(mEntity.getProperty("path").toString());
            response.setSize(mEntity.getProperty("size").toString());
        } catch(Exception e) {
            //DO NOTHING
        }

        return response;
    }

    @ApiMethod(name = "userPost")
    public MyRoom userPost(@Named("room") String room,@Named("paint") String paint,@Named("path") String path,@Named("canvas") String canvas) {
        MyRoom response = new MyRoom();
        response.setRoom(room);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("room", Query.FilterOperator.EQUAL,room);
        Query mQuery = new Query("room")
                .setFilter(mFilter);

        try {
            Entity mEntity = datastore.prepare(mQuery)
                    .asSingleEntity();

            mEntity.setProperty("colour",paint);
            mEntity.setProperty("path",path);
            mEntity.setProperty("size",canvas);

            datastore.put(mEntity);
        } catch(Exception e) {
            //DO NOTHING
        }

        return response;
    }
    @ApiMethod(name = "clearScreen")
    public MyRoom clearScreen(@Named("room") String room,@Named("friend") String friend) {
        MyRoom response = new MyRoom();
        response.setRoom(room);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter mFilter = new Query.FilterPredicate("room", Query.FilterOperator.EQUAL,room);
        Query mQuery = new Query("room")
                .setFilter(mFilter);

        try {
            Entity mEntity = datastore.prepare(mQuery)
                    .asSingleEntity();

            mEntity.setProperty("colour","null");
            mEntity.setProperty("path","null");
            mEntity.setProperty("size","null");

            datastore.put(mEntity);
        } catch(Exception e) {
            //DO NOTHING
        }

        mFilter = new Query.FilterPredicate("room", Query.FilterOperator.EQUAL,friend);
        mQuery = new Query("room")
                .setFilter(mFilter);

        try {
            Entity mEntity = datastore.prepare(mQuery)
                    .asSingleEntity();

            mEntity.setProperty("colour","null");
            mEntity.setProperty("path","null");
            mEntity.setProperty("size","null");

            datastore.put(mEntity);
        } catch(Exception e) {
            //DO NOTHING
        }

        return response;
    }
}
