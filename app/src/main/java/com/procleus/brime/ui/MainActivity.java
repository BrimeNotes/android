package com.procleus.brime.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.procleus.brime.R;
import com.procleus.brime.login.SigninActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private boolean isFabOpen = false;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation show_fab,hide_fab,rotate_fab_forward,rotate_fab_backward;
    SharedPreferences sharedPreferences = null;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("com.procleus.brime", MODE_PRIVATE);

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
        //calling function to show fab
        showFloatingActionButton(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        final TextView profile = (TextView)hView.findViewById(R.id.name);
        String name = sharedPreferences.getString("emailpref", "Guest");
        profile.setText(name);

        Fragment fragment = new PublicFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).commit();
        getSupportActionBar().setTitle("Public NotesDbHelperOld");
    }

    public void showFloatingActionButton(boolean toggle){
        if(toggle==true){
            fab.show();
        }
        else{
            fab.hide();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab_1:
                Intent i = new Intent(MainActivity.this,CreateNotesActivity.class);
                startActivity(i);
                Log.d("Fab clicked ", "Fab 1");
                break;
            case R.id.fab_2:

                Log.d("Fab Clicked ", "Fab 2");
                break;
            case R.id.fab_3:
                Intent i1 = new Intent(MainActivity.this,CreateAudioNotesActivity.class);
                startActivity(i1);
                break;

        }
    }

    
    //nervehammer
     public void runDia(){
        // Alert Dialog
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("OOPS... you are not logged in.\n\nPlease login to access this feature");
        alertDialogBuilder.setNeutralButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //finish();
                Intent i = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(i);
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void popBack(){

            String name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            navigationView.getMenu().findItem(R.id.nav_public_notes).setChecked(true);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        int fragments = fragmentManager.getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (fragments > 0) {
           popBack();
            navigationView.getMenu().findItem(R.id.nav_public_notes).setChecked(true);
        }
        else {
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                 super.onBackPressed();
                // android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!",
                        Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
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
           displayView(R.id.nav_settings);
            navigationView.getMenu().findItem(R.id.nav_settings).setChecked(true);
        } else if (id == R.id.action_exit) {
            finish();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

//added nerverhammer
public void displayView(int viewId) {

    Fragment fragment = null;
    switch (viewId) {
        case R.id.nav_public_notes:
             fragment = new PublicFragment();
             navigationView.getMenu().findItem(R.id.nav_public_notes).setChecked(true);
            break;
        case R.id.nav_private_notes:
            fragment = new PrivateFragment();
            break;
        case R.id.nav_labels:
            fragment = new LabelsFragment();
            break;
        case R.id.nav_trash:
            fragment = new TrashFragment();
            break;
        case R.id.nav_settings:
            Intent setin = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(setin);
            //fragment = new SettingsFragment();
            break;
        case R.id.nav_sync:
            break;
        case R.id.nav_share:
            break;
        case R.id.nav_explore:
 		       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        	     if (drawer.isDrawerOpen(GravityCompat.START)) {
                 drawer.closeDrawer(GravityCompat.START);
             }
            Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
            intent.putExtra("from", "mainActivity");
            startActivity(intent);
            break;
        default:
            break;
    }
    if (fragment != null) {
       if (viewId == R.id.nav_public_notes) {
           FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).addToBackStack("home");
             ft.commit();
         }/*else if(viewId==R.id.nav_private_notes){
             FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).addToBackStack("private");
             ft.commit();
         }*/
         else {
             FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).addToBackStack(null);
             ft.commit();
         }
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
      }
   }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       if(sharedPreferences.getBoolean("loggedin", false)) {
             displayView(item.getItemId());
         }else {
             if(item.getItemId()==R.id.nav_private_notes){
                 runDia();
             }
             else displayView(item.getItemId());
         }
          return true;
      }
}


