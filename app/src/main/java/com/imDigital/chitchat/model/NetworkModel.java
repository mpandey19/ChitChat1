package com.imDigital.chitchat.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NetworkModel {

    public String getJsonBody() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);

    }
}
