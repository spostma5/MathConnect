package com.example.Kyoukasuigetsu.mathconnect.backend;

/**
 * Created by Kyoukasuigetsu on 22/02/2015.
 */
public class MyRoom {

    private String room, friends, colour, path, size;

    public String getRoom() {
        return room;
    }

    public String getFriends() {
        return friends;
    }

    public String getRoomAll() {
        String toReturn = room + ";=;" + friends;

        return toReturn;
    }

    public String getDrawing() {
        return colour + ";=;" + path + ";=;" + size;
    }

    public void setRoom(String newRoom) {
        room = newRoom;
    }

    public void setColour(String newPaint) {
        colour = newPaint;
    }

    public void setPath(String newPath) {
        path = newPath;
    }

    public void setFriends(String newFriends) {
        friends = newFriends;
    }

    public void setSize(String newCanvas) {
        size = newCanvas;
    }
}