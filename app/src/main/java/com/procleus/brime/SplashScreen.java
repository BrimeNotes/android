package com.procleus.brime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this,IntroActivity.class);
                startActivity(mainIntent);
                SplashScreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}