package com.imDigital.chitchat.activity;

/**
 * Created by manvendrapandey on 27/02/18.
 */

public class Channel {

    private String id;
    private String text;
    private String name;
    private String image;

    public Channel() {
    }

    public Channel(String text, String name, String image) {
        this.text = text;
        this.name = name;
            this.image = image;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
