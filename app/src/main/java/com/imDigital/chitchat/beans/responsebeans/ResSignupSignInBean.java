package com.imDigital.chitchat.beans.responsebeans;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by madstech on 11/13/17.
 */

public class  ResSignupSignInBean {

    public String status;
    public String message;
    public Integer code;
    public ResponseData response;
    public String requestKey;

    public static ResSignupSignInBean fromJson(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, ResSignupSignInBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class ResponseData implements Serializable{
        public String id;
        public String name;
        public String email;
        public String password;
        public String tmp_password;
        public String createdOn;
        public String token;
    }

    /*{
        "status": "SUCCESS",
            "message": "Signup Successful.",
            "code": 200,
            "requestKey": "signup",
            "response": {
        "id": "1",
                "name": "deepak",
                "email": "deepakc1112@gmail.com",
                "password": "d8578edf8458ce06fbc5bb76a58c5ca4",
                "tmp_password": "qwerty",
                "createdOn": "2017-11-11 14:16:33",
                "token": "5a06b8e9-4d8c-458d-a184-0d349c9dec0d"
    }
    }*/
}
