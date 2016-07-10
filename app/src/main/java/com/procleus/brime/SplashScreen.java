package com.procleus.brime;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SharedPreferences sharedPreferences;
    ImageView mImageView;	//reference to the ImageView
    int xDim, yDim;		//stores ImageView dimensions
    private bitmapCreate bitmap;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        sharedPreferences = getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
        setContentView(R.layout.activity_splash);


        /*
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        */
        mImageView=(ImageView)findViewById(R.id.splashscreen_logo);
        xDim=200;
        yDim=200;
        mImageView.setImageBitmap(bitmap.decodeSampledBitmapFromResource(getResources(), R.drawable.logodark, xDim, yDim));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (sharedPreferences.getBoolean("firstrun", true)) {
                    Intent mainIntent = new Intent(SplashScreen.this,IntroActivity.class);
                    startActivity(mainIntent);
                    SplashScreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    sharedPreferences.edit().putBoolean("firstrun", false).commit();
                    finish();
                }
                else {
                    preLogInCheck();
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
    }

    public void preLogInCheck(){
        //== Support Offline Session==
        SharedPreferences clogin = getApplicationContext().getSharedPreferences(SigninActivity.PREF, MODE_PRIVATE);
        if(clogin.getBoolean("loggedin", false)){
            Intent i=new Intent(SplashScreen.this,MainActivity.class);
            startActivity(i);
        }else{
            Intent mainIntent = new Intent(SplashScreen.this,SigninActivity.class);
            startActivity(mainIntent);
        }
    }
}
