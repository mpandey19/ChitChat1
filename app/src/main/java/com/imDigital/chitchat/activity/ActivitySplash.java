package com.imDigital.chitchat.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.imDigital.chitchat.R;
import com.imDigital.chitchat.preferences.AppPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        AppPreferences.INSTANCE.initAppPreferences(getApplicationContext());

        generateKeyHash();

        generatDeviceId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreferences.INSTANCE.isLogin()){
                    startActivity(new Intent(ActivitySplash.this, MainActivity.class));
                }
                else{
                    startActivity(new Intent(ActivitySplash.this, ActivitySignin.class));
                }
                //startActivity(new Intent(ActivitySplash.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

    private void generatDeviceId() {
        String mDeviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppPreferences.INSTANCE.setDeviceID(mDeviceId);
        Log.e("DeviceId", "" + mDeviceId);
    }

    private void generateKeyHash() {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo("com.gaptechworks.chitchat", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:::", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
