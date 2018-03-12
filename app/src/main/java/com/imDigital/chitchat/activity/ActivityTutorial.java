package com.imDigital.chitchat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.imDigital.chitchat.R;

public class ActivityTutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);
    }

    public void onSignup(View view) {
        startActivity(new Intent(this, ActivitySignup.class));
    }

    public void onSignin(View view) {
        startActivity(new Intent(this, ActivitySignin.class));
    }
}
