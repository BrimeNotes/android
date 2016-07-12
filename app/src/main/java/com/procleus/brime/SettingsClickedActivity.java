package com.procleus.brime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        Fragment fragment = new AccountInfoFragment();
        if (position == 0) {
            getSupportActionBar().setTitle("Account Info");
            fragment = new AccountInfoFragment();
        } else if (position == 1) {
            getSupportActionBar().setTitle("Change Password");
            fragment = new ChangePasswordFragment();
        } else if (position == 2) {
            Sign_Out();
        } else if (position == 3) {
            //Shareapp
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
        finish();

    }

    public void Sign_Out(){
        SharedPreferences sharedpreferences = getSharedPreferences(SigninActivity.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        finish();
        Intent i= new Intent(SettingsClickedActivity.this,SigninActivity.class);
        startActivity(i);
    }
     public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
