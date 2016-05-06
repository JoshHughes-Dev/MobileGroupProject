package com.mobilegroupproject.studentorganiser.activities;


import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.fragments.DaysCalendarFragment;
import com.mobilegroupproject.studentorganiser.fragments.WeeksCalendarFragment;
import com.mobilegroupproject.studentorganiser.listeners.NavDrawerItemSelectedListener;


public class CalendarActivity extends AppCompatActivity
        implements WeeksCalendarFragment.OnFragmentInteractionListener,
        DaysCalendarFragment.OnFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitDrawer(toolbar);

        InitCalendarFragments(savedInstanceState);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_logout:
                break;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void InitDrawer(Toolbar toolbar){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(drawer != null){
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null){
            navigationView.setNavigationItemSelectedListener(
                    new NavDrawerItemSelectedListener(getApplicationContext(), drawer, getSupportFragmentManager()));
            navigationView.setCheckedItem(R.id.nav_cal_days);
        }

    }

    /*
    * Creates new or existing instances of weeks and days calendar fragments and adds to container.
    * hides weeks and shows days by default
    * */
    private void InitCalendarFragments(Bundle savedInstanceState){

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.calendar_fragment_container) != null) {

            if(savedInstanceState == null){

                // Create a new Fragment to be placed in the activity layout
                WeeksCalendarFragment weeksCalendarFragment = WeeksCalendarFragment.newInstance();
                DaysCalendarFragment daysCalendarFragment = DaysCalendarFragment.newInstance();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                weeksCalendarFragment.setArguments(getIntent().getExtras());
                daysCalendarFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container'
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.calendar_fragment_container, weeksCalendarFragment, getString(R.string.weeks_fragment_tag))
                        .add(R.id.calendar_fragment_container, daysCalendarFragment, getString(R.string.days_fragment_tag))
                        .hide(weeksCalendarFragment)
                        .addToBackStack(null)
                        .commit();
            }

        }

    }

    @Override
    public void onDaysFragmentInteraction(){

    }

    @Override
    public void onWeeksFragmentInteraction(){

    }

}
