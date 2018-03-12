package com.imDigital.chitchat.interfaces;

/**
 * Created by Gaurava.
 */
public interface GNetworkEvent {
    void onNetworkCallInitiated(String service);

    void onNetworkCallCompleted(String service, String response);

    void onNetworkCallError(String service, String errorMessage);
}
