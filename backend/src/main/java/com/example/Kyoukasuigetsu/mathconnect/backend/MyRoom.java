package com.example.Kyoukasuigetsu.mathconnect.backend;

/**
 * Created by Kyoukasuigetsu on 22/02/2015.
 */
public class MyRoom {

    private String room, friends, paint, path, canvas;

    public String getRoom() {
        return room;
    }

    public String getFriends() {
        return friends;
    }

    public String getRoomAll() {
        String toReturn = room + ";=;" + friends + ";=;" + paint + ";=;" + path + ";=;" + canvas;

        return toReturn;
    }

    public String getDrawing() {
        return paint + ";=;" + path + ";=;" + canvas;
    }

    public void setRoom(String newRoom) {
        room = newRoom;
    }

    public void setPaint(String newPaint) {
        paint = newPaint;
    }

    public void setPath(String newPath) {
        path = newPath;
    }

    public void setFriends(String newFriends) {
        friends = newFriends;
    }

    public void setCanvas(String newCanvas) {
        canvas = newCanvas;
    }
}