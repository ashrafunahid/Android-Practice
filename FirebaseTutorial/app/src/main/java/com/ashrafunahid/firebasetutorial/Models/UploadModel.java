package com.ashrafunahid.firebasetutorial.Models;

import com.google.firebase.database.Exclude;

public class UploadModel {
    private String name;
    private String imageUrl;
    private String uniqueKey;

//    Creating an empty constructor
    public UploadModel() {
//        As need an empty constructor
    }

//    Creating constructor
    public UploadModel(String name, String imageUrl) {
        if(name.trim().equals("")) {
            name = "No Name";
        }
        this.name = name;
        this.imageUrl = imageUrl;
    }

//    Creating getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Exclude
    public String getUniqueKey() {
        return uniqueKey;
    }

    @Exclude
    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
