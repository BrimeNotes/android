/**
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 * @author Swastik Binjola <swastik.binjola2561@gmail.com>
 * @author Ujjwal Bhardwaj <ujjwalb1996@gmail.com>
 *
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.procleus.brime.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.procleus.brime.R;
import com.procleus.brime.utils.BitmapCreate;
import com.procleus.brime.login.SigninActivity;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SharedPreferences sharedPreferences;
    SharedPreferences clogin;
    ImageView mImageView;   //reference to the ImageView
    int xDim, yDim;     //stores ImageView dimensions
    private BitmapCreate bitmap;

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

