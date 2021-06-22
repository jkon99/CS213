package com.example.android29.model;

import android.app.Person;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Photo implements Serializable {
    private String path;
    private String location;
    private String person;

    public Photo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String loc) {
        location = loc;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String p) {
        person = p;
    }


}
