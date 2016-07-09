package com.procleus.brime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private boolean isFabOpen = false;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation show_fab,hide_fab,rotate_fab_forward,rotate_fab_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ===  FAB REFRENCES ===
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab_3);
        // FAB Animations
        show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_open);
        hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_close);
        rotate_fab_forward = AnimationUtils.loadAnimation(getApplication(),R.anim.rotate_fab_forward);
        rotate_fab_backward = AnimationUtils.loadAnimation(getApplication(),R.anim.rotate_fab_backward);
        fab.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab1.setOnClickListener(this);
        // FAB END
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab_1:

                Log.d("Fab clicked ", "Fab 1");
                break;
            case R.id.fab_2:

                Log.d("Fab Clicked ", "Fab 2");
                break;
        }
    }

    /* Function for Animation of Fab */
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_fab_backward);
            FrameLayout.LayoutParams layoutParamsFab1 = (FrameLayout.LayoutParams) fab1.getLayoutParams();
            FrameLayout.LayoutParams layoutParamsFab2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            FrameLayout.LayoutParams layoutParamsFab3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
            //layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
            //layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
            //layoutParams.rightMargin -= (int) (fab1.getHeight() * 0.2);
            layoutParamsFab1.bottomMargin -= (int) (fab1.getHeight() * 1.8);
            fab1.setLayoutParams(layoutParamsFab1);
            fab1.startAnimation(hide_fab);
            fab1.setClickable(false);

            layoutParamsFab2.bottomMargin -= (int) (fab2.getHeight() * 3);
            fab2.setLayoutParams(layoutParamsFab2);
            fab2.startAnimation(hide_fab);
            fab2.setClickable(false);

            layoutParamsFab3.bottomMargin -= (int) (fab3.getHeight() * 4.2);
            fab3.setLayoutParams(layoutParamsFab3);
            fab3.startAnimation(hide_fab);
            fab3.setClickable(false);
            isFabOpen = false;

        } else {
            fab.startAnimation(rotate_fab_forward);
            FrameLayout.LayoutParams layoutParamsFab1 = (FrameLayout.LayoutParams) fab1.getLayoutParams();
            FrameLayout.LayoutParams layoutParamsFab2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
            FrameLayout.LayoutParams layoutParamsFab3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

            layoutParamsFab1.rightMargin = (int) (fab1.getWidth() * 0.7);
            layoutParamsFab1.bottomMargin += (int) (fab1.getHeight() * 1.8);
            fab1.setLayoutParams(layoutParamsFab1);
            fab1.startAnimation(show_fab);
            fab1.setClickable(true);

            layoutParamsFab2.rightMargin = (int) (fab2.getWidth() * 0.7);
            layoutParamsFab2.bottomMargin += (int) (fab2.getHeight() * 3);
            fab2.setLayoutParams(layoutParamsFab2);
            fab2.startAnimation(show_fab);
            fab2.setClickable(true);


            layoutParamsFab3.rightMargin = (int) (fab3.getWidth() * 0.7);
            layoutParamsFab3.bottomMargin += (int) (fab3.getHeight() * 4.2);
            fab3.setLayoutParams(layoutParamsFab3);
            fab3.startAnimation(show_fab);
            fab3.setClickable(true);

            isFabOpen = true;
        }
    }
    /* Fab Animation Ends*/

    @Override
    public void onBackPressed() {
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

    //Added settitle function nervehammer
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
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
            fragment = new PublicFragment();
        } else if (id == R.id.nav_private_notes) {
            fragment = new PrivateFragment();

        } else if (id == R.id.nav_labels) {
            fragment = new LabelsFragment();
        } else if (id == R.id.nav_trash) {
            fragment = new TrashFragment();
        } else if (id == R.id.nav_settings) {
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
