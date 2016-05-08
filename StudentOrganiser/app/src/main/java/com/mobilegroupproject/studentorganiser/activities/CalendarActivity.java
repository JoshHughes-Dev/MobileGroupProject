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

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.WeekView;

import com.mobilegroupproject.studentorganiser.R;

import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEmptyViewLongPressListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEventClickListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEventLongPressListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarMonthChangeListener;
import com.mobilegroupproject.studentorganiser.listeners.NavDrawerItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {


    private WeekView calendarWeekView;

    protected boolean dualPane = false;
    protected String lastSelectedEvent = "";
    private final String lastSelectedEventFlag = "lastSelectedEventId";
    private final String currentNumOfDaysFlag = "currentNumOfDays";

    public int currentNumOfDays = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            currentNumOfDays = savedInstanceState.getInt(currentNumOfDaysFlag, 1);
            lastSelectedEvent = savedInstanceState.getString(lastSelectedEventFlag);
        }

        initDrawer(toolbar);

        initAddNewEntryButton();

        initCalendarWidget();


        // Check if the FrameLayout with the id details exists
        View detailsFrame = findViewById(R.id.frame_event_details);
        // Set dualPane based on whether you are in the horizontal layout
        // Check if the detailsFrame exists and if it is visible
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (dualPane) {
            // Send the item selected to showDetails so the right info is shown
            showEventDetails(lastSelectedEvent);
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
        outState.putString(lastSelectedEventFlag, lastSelectedEvent);
        outState.putInt(currentNumOfDaysFlag, currentNumOfDays);
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
                    new NavDrawerItemSelectedListener(CalendarActivity.this, drawer, getSupportFragmentManager()));
            navigationView.setCheckedItem(R.id.nav_cal_day);
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



    private void initCalendarWidget(){

        // Get a reference for the week view in the layout.
        calendarWeekView = (WeekView) findViewById(R.id.calander_weekView_widget);

        calendarWeekView.setNumberOfVisibleDays(currentNumOfDays); //set inital layout format
        setupDateTimeInterpreter(currentNumOfDays); //set inital format


        calendarWeekView.setNowLineColor(R.color.colorAccent);
        calendarWeekView.setShowNowLine(true);
        calendarWeekView.setShowDistinctWeekendColor(true);

        setCalendarWeekViewCurrentHour();

        calendarWeekView.setOnEventClickListener(new CalendarEventClickListener(this));

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        calendarWeekView.setMonthChangeListener(new CalendarMonthChangeListener(this));

        // Set long press listener for events.
        calendarWeekView.setEventLongPressListener(new CalendarEventLongPressListener());

        // Set long press listener for empty view
        calendarWeekView.setEmptyViewLongPressListener(new CalendarEmptyViewLongPressListener());
    }



    /*
    * TODO: Shows details data for single event
    * */
    public void showEventDetails( String eventName) {

        Log.d("calendarActivity", Boolean.toString(dualPane));

        // The most recently selected event
        lastSelectedEvent = eventName;

        // Check if we are in horizontal mode and if yes show dual pane
        if (dualPane) {

            // Create an object that represents the current FrameLayout that we will put the event data in
            EventDetailsFragment eventDetailsFragment = (EventDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_event_details);

            // When a DetailsFragment is created by calling newInstance the index for the data
            // it is supposed to show is passed to it. If that index hasn't been assigned we must
            // assign it in the if block
            if (eventDetailsFragment == null || !eventDetailsFragment.getEventName().equalsIgnoreCase(eventName)) {

                // Make the details fragment and give it the currently selected hero index
                eventDetailsFragment = EventDetailsFragment.newInstance(eventName);

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
            intent.putExtra("eventName", eventName);

            // Call for the Activity to open
            startActivity(intent);
        }
    }

    public void setupDateTimeInterpreter(final int currentNumOfDays) {

        calendarWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {


                String weekdayStringFormat = "EEEE";
                String dateStringFormat = "d MMM";

                switch(currentNumOfDays){
                    case 1:
                        //use defaults
                        break;
                    case 3:
                        weekdayStringFormat = "EEE";
                        dateStringFormat = "d/M";
                        break;
                    case 7:
                        weekdayStringFormat = "EEEEE";
                        dateStringFormat = "d/M";
                        break;
                }

                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat(weekdayStringFormat, Locale.UK);
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(dateStringFormat, Locale.UK);


                return weekday.toUpperCase() + " " + format.format(date.getTime());

            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }


    public void setCalendarWeekViewCurrentHour(){

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        calendarWeekView.goToHour(currentHour);
    }

}
