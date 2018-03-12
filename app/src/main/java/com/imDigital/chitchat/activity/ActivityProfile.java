package com.imDigital.chitchat.activity;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imDigital.chitchat.R;

public class ActivityProfile extends AppCompatActivity {

    private LinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        parent = (LinearLayout) toolbar.findViewById(R.id.parent);
        setSupportActionBar(toolbar);
        setToolbarTitle("Profile");
    }

    public void setToolbarTitle(String feature){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        if (parent.getChildCount()>0){
            parent.removeAllViews();
        }
        /*ImageView iv = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        iv.setLayoutParams(lp);
        iv.setId(5551);
        iv.setImageResource(R.drawable.icon_logo_header);*/

        TextView tv = new TextView(getApplicationContext());
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER_HORIZONTAL;
        lp1.setMargins(0,3,0,3);
        tv.setLayoutParams(lp1);
        tv.setText(feature);
        tv.setTextSize(14);
        tv.setTextColor(getResources().getColor(R.color.white));
        /*Typeface tf = Typeface.createFromAsset(getAssets(), "font/Raleway-Light.ttf");
        tv.setTypeface(tf);
        parent.addView(iv);*/
        parent.addView(tv);
    }
}
