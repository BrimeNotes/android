package com.procleus.brime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SharedPreferences sharedPreferences;
    SharedPreferences clogin;
    ImageView mImageView;   //reference to the ImageView
    int xDim, yDim;     //stores ImageView dimensions
    private bitmapCreate bitmap;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        sharedPreferences = getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
        setContentView(R.layout.activity_splash);
        clogin = getApplicationContext().getSharedPreferences(SigninActivity.PREF, MODE_PRIVATE);

        /*
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        */
        mImageView = (ImageView) findViewById(R.id.splashscreen_logo);
        xDim = 200;
        yDim = 200;
        mImageView.setImageBitmap(bitmap.decodeSampledBitmapFromResource(getResources(), R.drawable.logodark, xDim, yDim));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getBoolean("firstrun", true)) {
                    Intent mainIntent = new Intent(SplashScreen.this, IntroActivity.class);
                    startActivity(mainIntent);
                    SplashScreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    sharedPreferences.edit().putBoolean("firstrun", false).commit();
                    finish();
                } else {
                    preLogInCheck();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
    }

    public void preLogInCheck() {
        //== Support Offline Session==
        SharedPreferences clogin = getApplicationContext().getSharedPreferences(SigninActivity.PREF, MODE_PRIVATE);
        //fetch data from sharedPref session
        String uid=clogin.getString("emailpref",null);
        String password=clogin.getString("passwordpref",null);
        if (clogin.getBoolean("loggedin", false) && isNetworkAvailable()) {
            Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else if (clogin.getBoolean("loggedin", false) && !isNetworkAvailable()) {
            Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {

            Intent mainIntent = new Intent(SplashScreen.this, SigninActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

