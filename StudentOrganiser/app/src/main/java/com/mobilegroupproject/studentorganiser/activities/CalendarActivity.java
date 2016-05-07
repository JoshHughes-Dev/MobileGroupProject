package com.mobilegroupproject.studentorganiser.activities;


import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobilegroupproject.studentorganiser.R;

import com.mobilegroupproject.studentorganiser.fragments.DayFragment;
import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;
import com.mobilegroupproject.studentorganiser.listeners.NavDrawerItemSelectedListener;


public class CalendarActivity extends AppCompatActivity implements DayFragment.OnFragmentInteractionListener {


    protected boolean dualPane = false;
    protected int lastSelectedEventId = 0;
    private final String lastSelectedEventIdFlag = "lastSelectedEventID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer(toolbar);

        initAddNewEntryButton();


        // Check if the FrameLayout with the id details exists

        View detailsFrame = findViewById(R.id.frame_event_details);
        // Set dualPane based on whether you are in the horizontal layout
        // Check if the detailsFrame exists and if it is visible
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        // If the screen is rotated onSaveInstanceState() below will store the
        // event data most recently selected. Get the value attached to curChoice and
        // store it in lastSelectedEventId
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            lastSelectedEventId = savedInstanceState.getInt(lastSelectedEventIdFlag, 0);
        }

        if (dualPane) {
            // Send the item selected to showDetails so the right info is shown
            showEventDetails(lastSelectedEventId);
        }
    }

    /* handles navigation button click*/
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


    /* creates nav bar pop up menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }


    /* handles nav bar popup menu items*/
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(lastSelectedEventIdFlag, lastSelectedEventId);
    }


    /* Initialises navigation drawer and binds navigation item select listener */
    private void initDrawer(Toolbar toolbar){

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




    /* initialises 'add new entry button' (floating button) */
    private void initAddNewEntryButton(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CalendarActivity.this, "will open 'new entry' view", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




    //fragment interface for day fragment selection.
    // TODO pass event id though here
    @Override
    public void onDayFragmentEventSelected(int index){
        showEventDetails(index);
    }


    /*
    * TODO: Shows details data for single event
    * */
    protected void showEventDetails(int eventId) {

        // The most recently selected event
        lastSelectedEventId = eventId;

        // Check if we are in horizontal mode and if yes show dual pane
        if (dualPane) {

            // Create an object that represents the current FrameLayout that we will put the event data in
            EventDetailsFragment eventDetailsFragment = (EventDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_event_details);

            // When a DetailsFragment is created by calling newInstance the index for the data
            // it is supposed to show is passed to it. If that index hasn't been assigned we must
            // assign it in the if block
            if (eventDetailsFragment == null || eventDetailsFragment.getShownIndex() != eventId) {

                // Make the details fragment and give it the currently selected hero index
                eventDetailsFragment = EventDetailsFragment.newInstance(eventId);

                // Start Fragment transactions
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                // Replace any other Fragment with our new Details Fragment with the right data
                ft.replace(R.id.frame_event_details, eventDetailsFragment);

                ft.commit();
            }

        } else {

            // Launch a new Activity to show our DetailsFragment
            Intent intent = new Intent();

            // Define the class Activity to call
            intent.setClass(CalendarActivity.this, EventDetailsActivity.class);

            // Pass along the currently selected index assigned to the keyword index
            intent.putExtra("index", eventId);

            // Call for the Activity to open
            startActivity(intent);
        }
    }


}
