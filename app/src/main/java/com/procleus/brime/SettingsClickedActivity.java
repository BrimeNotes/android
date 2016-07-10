package com.procleus.brime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Ujjwal on 09-07-2016.
 */
public class SettingsClickedActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        Bundle extras = getIntent().getExtras();
        int position;
        position = Integer.parseInt(extras.getString("a"));
        Fragment fragment = null;
        if (position == 0) {
            getSupportActionBar().setTitle("Account Info");
            fragment = new AccountInfoFragment();
        } else if (position == 1) {
            getSupportActionBar().setTitle("Change Password");
            fragment = new ChangePasswordFragment();
        } else if (position == 2) {
            // Share App
        } else if (position == 3) {
            // Sign Out
        } else if (position == 4) {
            getSupportActionBar().setTitle("About Us");
            fragment = new AboutUsFragment();
        }
        //adding fragments to stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_settings_relative, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
