/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Kyoukasuigetsu.mathconnect.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.mathconnect.Kyoukasuigetsu.example.com", ownerName = "backend.mathconnect.Kyoukasuigetsu.example.com", packagePath = ""))
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
        if(user.equals("Sam@google.ca") && pass.equals("password")) {
            response.setUsername("Sam@google.ca");
            response.setPassword("password");
        }
        else if(user.equals("Sam@google.ca")) {
            response.setUsername("VALID");
            response.setUsername("INVALID");
        }
        else {
            response.setUsername("INVALID");
            response.setUsername("VALID");
        }

        return response;
    }

}
