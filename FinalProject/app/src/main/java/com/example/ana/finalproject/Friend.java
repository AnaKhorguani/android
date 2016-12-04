package com.example.ana.finalproject;

import android.graphics.Bitmap;

import java.util.ArrayList;


public class Friend {
    private int id;
    private String name;
    private String number;
    private Bitmap avatar;

    public String color="#ffffff";

    public ArrayList<String> messages=new ArrayList<String>();

    public Friend(){}
    /*public Friend (int id, ArrayList<String> messages)
    {
        this.id=id;
        this.messages=messages;
    }*/
    public Friend(int id, String name, String number, Bitmap avatar)
    {
        this.id=id;
        this.name=name;
        this.number=number;
        this.avatar=avatar;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
