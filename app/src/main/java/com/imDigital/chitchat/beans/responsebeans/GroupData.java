package com.imDigital.chitchat.beans.responsebeans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gaurava Mishra on 1/27/2018.
 */

public class GroupData implements Serializable {
    public String gname;
    public String guser_status;
    public String gcreator;
    public String glocation;
    public String gid;
    public String gmember_count;
    public ArrayList<Threads> gthreads;
}
