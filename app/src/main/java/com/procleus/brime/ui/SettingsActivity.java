/**
 * @author Suraj Rawat <suraj.raw120@gmail.com>
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

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.procleus.brime.R;
import com.procleus.brime.login.SigninActivity;

public class SettingsActivity extends AppCompatActivity{

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setActionBarTitle("Settings");
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            setActionBarTitle("Settings");
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment(),"Settings")
                .commit();

    }

    public void signOut(){
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
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}