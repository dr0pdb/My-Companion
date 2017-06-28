package com.example.srv_twry.studentcompanion.POJOs;

/**
 * Created by srv_twry on 17/6/17.
 * The class to represent a feature in this app.
 */

public class Feature {
    private final String title;
    private final int imageResourceId;

    public Feature(String title,int imageResourceId){
        this.title= title;
        this.imageResourceId= imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
