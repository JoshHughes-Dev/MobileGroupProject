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

import com.alamkanak.weekview.WeekView;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;
import com.mobilegroupproject.studentorganiser.listeners.CalendarDateTimeInterpreter;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEmptyViewLongPressListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEventClickListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEventLongPressListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarMonthChangeListener;
import com.mobilegroupproject.studentorganiser.listeners.NavDrawerItemSelectedListener;
import com.mobilegroupproject.studentorganiser.model.ParcelableCalendarDate;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class CalendarActivity extends AppCompatActivity {


    private WeekView calendarWeekView;



    private final String lastSelectedEventIdFlag = "lastSelectedEventId";
    private final String currentNumOfDaysFlag = "currentNumOfDays";
    private final String lastViewedDateFlag = "lastViewedDate";

    public int currentNumOfDays = 1;
    protected int lastSelectedEventId = 0;
    protected boolean dualPane = false;
    protected ParcelableCalendarDate lastViewedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            currentNumOfDays = savedInstanceState.getInt(currentNumOfDaysFlag, 1);
            lastSelectedEventId = savedInstanceState.getInt(lastSelectedEventIdFlag);
            lastViewedDate = savedInstanceState.getParcelable(lastViewedDateFlag);
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
            case R.id.action_today:
                calendarWeekView.goToToday();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(lastSelectedEventIdFlag, lastSelectedEventId);
        outState.putInt(currentNumOfDaysFlag, currentNumOfDays);
        outState.putParcelable(lastViewedDateFlag, lastViewedDate);
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
                    new NavDrawerItemSelectedListener(CalendarActivity.this, drawer));
            //Pre-select nav item
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

        calendarWeekView.setDateTimeInterpreter(new CalendarDateTimeInterpreter(currentNumOfDays));

        //LISTENERS...

        // to get a callback when an event is pressed
        calendarWeekView.setOnEventClickListener(new CalendarEventClickListener(this));
        // to provide events to the calendar by months
        calendarWeekView.setMonthChangeListener(new CalendarMonthChangeListener(this));
        // to get a callback when an event is long pressed
        calendarWeekView.setEventLongPressListener(new CalendarEventLongPressListener());
        //to get a callback when any empty space is long pressed
        calendarWeekView.setEmptyViewLongPressListener(new CalendarEmptyViewLongPressListener());



        calendarWeekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                updateLastViewedDateAndTime(newFirstVisibleDay);
            }
        });



        //view configuration...

        if(calendarWeekView != null) {
            calendarWeekView.setNumberOfVisibleDays(currentNumOfDays);
            calendarWeekView.setNowLineColor(R.color.colorAccent);
            calendarWeekView.setShowNowLine(true);
            calendarWeekView.setShowDistinctWeekendColor(true);
            setCalendarWeekViewDateAndTime();
        }

    }



    /*
    * TODO: Shows details data for single event
    * */
    public void showEventDetails(int eventId) {

        // The most recently selected event
        lastSelectedEventId = eventId;

        // Check if we are in horizontal mode and if yes show dual pane layout
        if (dualPane) {

            //get existing fragment from frame
            EventDetailsFragment eventDetailsFragment = (EventDetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frame_event_details);


            //if it doesnt exist or it isnt same as fragment already in there then...
            if (eventDetailsFragment == null || eventDetailsFragment.getEventId() != lastSelectedEventId) {

                // Make new fragment with selected data
                eventDetailsFragment = EventDetailsFragment.newInstance(eventId);

                // replace old fragment with new fragment
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_event_details, eventDetailsFragment);
                ft.commit();
            }

        } else {

            // Launch a new Activity to show our DetailsFragment
            Intent intent = new Intent();
            intent.setClass(CalendarActivity.this, EventDetailsActivity.class);

            // Pass along the currently selected index assigned to the keyword index
            intent.putExtra(EventDetailsFragment.eventIdFlag, eventId);

            startActivity(intent);
        }
    }




    public void setCalendarWeekViewDateAndTime(){


        if(lastViewedDate == null){
            //creates 'TODAY' date and sets lastViewedDate
            Date date = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            updateLastViewedDateAndTime(calendar);
        }


        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(lastViewedDate.Year,
                lastViewedDate.Month,
                lastViewedDate.Date,
                lastViewedDate.Hour,
                0);

        calendarWeekView.goToDate(calendar);
        calendarWeekView.goToHour(lastViewedDate.Hour);

    }

    private void updateLastViewedDateAndTime(Calendar calendar){
        if(lastViewedDate == null) {
            lastViewedDate = new ParcelableCalendarDate();
        }

        lastViewedDate.Year = calendar.get(Calendar.YEAR);
        lastViewedDate.Month = calendar.get(Calendar.MONTH);
        lastViewedDate.Date = calendar.get(Calendar.DATE);

        //have to use current hour because calander week view doesnt store current hour
        Date date = new Date();
        Calendar hourCalendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        lastViewedDate.Hour = hourCalendar.get(Calendar.HOUR_OF_DAY);
    }



}
