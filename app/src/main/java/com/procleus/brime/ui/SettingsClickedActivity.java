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

import com.procleus.brime.R;
import com.procleus.brime.login.SigninActivity;

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
