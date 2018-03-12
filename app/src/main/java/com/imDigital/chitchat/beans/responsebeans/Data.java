package com.imDigital.chitchat.beans.responsebeans;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Gaurava Mishra on 1/27/2018.
 */

public class Data implements Serializable {

    public UserData user = new UserData();
    public ArrayList<GroupData> group;
}
