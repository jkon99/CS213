package com.example.android29.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable{
    private List<Photo> photos;
    private String name;

    public Album(String name) {
        photos = new ArrayList<>();

        this.name = name;
    }

    @Override @NonNull
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos(){
        return photos;

    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public void deletePhoto(Photo photo) {
        photos.remove(photo);
    }

    public boolean containsPhoto(Photo photo) {
        for(Photo p: photos) {
            if(p.getPath().equals(photo.getPath())) {
                return true;
            }
        }
        return false;
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    public String numberOfPhotos(){
        int number = 0;
        for(Photo p: photos) number++;
        return Integer.toString(number);
    }
}
