package com.imDigital.chitchat.beans.responsebeans;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gaurava on 11/13/17.
 */

public class ResPostListingBean {

    public String status;
    public String message;
    public Integer code;
    public ArrayList<ResponseData> response;
    public String requestKey;

    public static ResPostListingBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, ResPostListingBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class ResponseData implements Serializable{
        public String id;
        public String user_id;
        public String title;
        public String message;
        public String createdOn;
    }

    /*{
        "status": "SUCCESS",
            "message": "Signup Successful.",
            "code": 200,
            "requestKey": "signup",
            "response" : {
            "id": "1",
            "user_id": "1",
            "title": "title 1",
            "message": "just testing",
            "createdOn": "2017-11-11 22:10:46"
        }
    }*/
}
