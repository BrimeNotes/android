package com.procleus.brime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = new PublicFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).commit();
        getSupportActionBar().setTitle("Public Notes");
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(MainActivity.this, "You cannot Exit", Toast.LENGTH_SHORT).show();
        }*/
        //Getting fragment from stack when pressed back button 
        //if all fragments popped then if back pressed twice performs exit
       
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount()==0) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                int fragments = fragmentManager.getBackStackEntryCount();
                if (fragments > 0) {
                    super.onBackPressed();
                } else {
                    if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                        super.onBackPressed();
                    } else {
                        Toast.makeText(getBaseContext(), "Press once again to exit!",
                                Toast.LENGTH_SHORT).show();
                    }
                    back_pressed = System.currentTimeMillis();
                }
            }
        } else {
            fragmentManager.popBackStack();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment fragment = new SettingsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).commit();
            getSupportActionBar().setTitle("Settings");
        } else if (id == R.id.action_exit) {
            finish();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new PublicFragment();

        if (id == R.id.nav_public_notes) {
            getSupportActionBar().setTitle("Public Notes");
            fragment = new PublicFragment();

        } else if (id == R.id.nav_private_notes) {
            getSupportActionBar().setTitle("Private Notes");
            fragment = new PrivateFragment();

        } else if (id == R.id.nav_labels) {
            getSupportActionBar().setTitle("Labels");
            fragment = new LabelsFragment();
        } else if (id == R.id.nav_trash) {
            getSupportActionBar().setTitle("Trash");
            fragment = new TrashFragment();
        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setTitle("Settings");
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_sync) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        //adding fragments to stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).addToBackStack(null).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
