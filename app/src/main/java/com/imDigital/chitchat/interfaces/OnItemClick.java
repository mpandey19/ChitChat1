package com.imDigital.chitchat.interfaces;

import android.view.View;

/**
 * Created by Gaurava Mishra on 1/28/2018.
 */

public interface OnItemClick {
    void onItemClick(View item_view, int position);
    void onClickAction(String tuser_status, int position);
}
