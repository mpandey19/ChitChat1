package com.imDigital.chitchat.beans.responsebeans;

import com.google.gson.Gson;

/**
 * Created by Gaurava Mishra on 1/27/2018.
 */

public class ResNewLoginSignup {

    public String status;
    public String messege;
    public String access_token;
    public Data data = new Data();

    public static ResNewLoginSignup fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, ResNewLoginSignup.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
