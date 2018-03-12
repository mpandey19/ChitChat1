package com.imDigital.chitchat.activity;

/**
 * Created by manvendrapandey on 07/03/18.
 */

public class UserGroupInformation {

    String name;
    String Channels;
    String image;

    public UserGroupInformation() {
    }
    public UserGroupInformation(String name, String image) {

        this.name = name;
        this.image = image;
        //this.Channels = channels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChannels() {
        return Channels;
    }

    public void setChannels(String channels) {
        this.Channels = channels;
    }
}
