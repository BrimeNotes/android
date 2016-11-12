package com.procleus.brime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SettingsClickedActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        Bundle extras = getIntent().getExtras();
        int position;
        position = Integer.parseInt(extras.getString("a"));
        Fragment fragment =new Fragment();
        if (position == 0) {
            getSupportActionBar().setTitle("Account Info");
            fragment = new AccountInfoFragment();
        } else if (position == 1) {
            getSupportActionBar().setTitle("Change Password");
            fragment = new ChangePasswordFragment();
        } else if (position == 4) {
            Sign_Out();
        } else if (position == 2) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Thank You");
            alertDialogBuilder.setMessage("Thank You For Showing Your Interest in Sharing Our App");
            alertDialogBuilder.setNeutralButton("Thank You", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                        }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();
        } else if (position == 3) {
            getSupportActionBar().setTitle("About Us");
            fragment = new AboutUsFragment();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        editor.remove("emailpref");
        editor.remove("passwordpref");
        editor.remove("loggedin");
        editor.commit();
        Intent intent = new Intent(this, SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // intent.putExtra("EXIT", true);
        startActivity(intent);
          finish();
    }
     public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
