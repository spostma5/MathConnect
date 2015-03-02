package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by Kyoukasuigetsu on 01/03/2015.
 */
public class Room{
    private String roomName;
    private Paint paint;
    private Canvas canvas;
    private Path path;
    private ArrayList<String> friends;
    private Context context;

    public String getName() { return roomName; }
    public void setName(String newName) {roomName = newName;}

    public Room() {
        roomName = "";
        paint = new Paint();
        canvas = new Canvas();
        path = new Path();
        friends = new ArrayList<String>();
    }

    public Room(String room, Context newContext) {
        roomName = room.split(";=;")[0];
        paint = new Paint();
        canvas = new Canvas();
        path = new Path();
        friends = new ArrayList<String>();

        try {
            String friend = room.split(";=;")[1];
            for(int i = 0; i < friend.split("&&&").length; i++) {
                friends.add(friend.split("&&&")[i]);
            }
        }catch (Exception e) {
            //DO NOTHING
        }

        context = newContext;
    }
}
