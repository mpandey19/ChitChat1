package com.imDigital.chitchat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Gaurava, this class is use to check the status of internet
 *         connection that is it available or not and return.
 */

public class NetworkStatus {
    /**
     * this method check that is internet connection availabele or not
     *
     * @param context to get ConnectivityManager
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()){
                return true;
            }
        }
        return false;
    }
}
