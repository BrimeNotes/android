package com.procleus.brime;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreferences sharedPreferences = null;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        sharedPreferences = getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
        setContentView(R.layout.activity_splash);

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
                    Intent mainIntent = new Intent(SplashScreen.this,SigninActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
