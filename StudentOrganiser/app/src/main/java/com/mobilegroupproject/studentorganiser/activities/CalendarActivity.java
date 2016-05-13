package com.mobilegroupproject.studentorganiser.activities;


import android.Manifest;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

import com.alamkanak.weekview.WeekView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.mobilegroupproject.studentorganiser.CalenderUITestData;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.data.CalendarLoader;
import com.mobilegroupproject.studentorganiser.data.CalendarProvider;
import com.mobilegroupproject.studentorganiser.data.Event;
import com.mobilegroupproject.studentorganiser.data.EventsData;
import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;
import com.mobilegroupproject.studentorganiser.listeners.CalendarDateTimeInterpreter;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEmptyViewLongPressListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEventClickListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarEventLongPressListener;
import com.mobilegroupproject.studentorganiser.listeners.CalendarMonthChangeListener;
import com.mobilegroupproject.studentorganiser.listeners.NavDrawerItemSelectedListener;
import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;
import com.mobilegroupproject.studentorganiser.model.ParcelableCalendarDate;


import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CalendarActivity extends AppCompatActivity implements EventDetailsFragment.OnFragmentInteractionListener
        , LoaderManager.LoaderCallbacks<List<Event>> {

    private final String LAST_SELECTED_EVENT_ID = "lastSelectedEventId";
    private final String CURRENT_NUM_OF_DAYS = "currentNumOfDays";
    private final String LAST_VIEWED_DATE = "lastViewedDate";
    private final String EVENTS_DATA = "eventsData";

    private GoogleApiClient googleApiClient;

    private WeekView calendarWeekView;
    public int currentNumOfDays = 1;
    protected int lastSelectedEventId = 0;
    protected boolean dualPane = false;
    protected ParcelableCalendarDate lastViewedDate;
    protected ArrayList<ExtendedWeekViewEvent> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            currentNumOfDays = savedInstanceState.getInt(CURRENT_NUM_OF_DAYS, 1);
            lastSelectedEventId = savedInstanceState.getInt(LAST_SELECTED_EVENT_ID);
            lastViewedDate = savedInstanceState.getParcelable(LAST_VIEWED_DATE);
            events = savedInstanceState.getParcelableArrayList(EVENTS_DATA);
        }

        initDataSource();

        initDrawer(toolbar);

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

        getLoaderManager().initLoader(0, null, this);   // Initialise the loader

        googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();

/*        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation!=null) {
                double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
                Log.d("lat1:", String.valueOf(lat));
                Log.d("lat2:", String.valueOf(lon));
            } else {
                Log.d("LOCATION IS NULL","..");
            }
        }*/

    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        //try {

        //} catch (SecurityException e) {
            // Har har har
        //}
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public void onConnected(Bundle bundle) {
/*        try {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
        Log.d("lat1:",String.valueOf(lat));
        Log.d("lat2:",String.valueOf(lon));

    } catch (SecurityException e) {
        // Har har har
    }*/
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
        outState.putInt(LAST_SELECTED_EVENT_ID, lastSelectedEventId);
        outState.putInt(CURRENT_NUM_OF_DAYS, currentNumOfDays);
        outState.putParcelable(LAST_VIEWED_DATE, lastViewedDate);
        outState.putParcelableArrayList(EVENTS_DATA, events);
    }

    private void initDataSource(){
        if(events == null || events.size() == 0) {

            CalendarProvider calendarProvider = new CalendarProvider(getApplicationContext());
            List<Event> providerEvents = calendarProvider.getAllEvents(calendarProvider.getCalendarDetails());
            events= new ArrayList<>();

            for(int i = 0; i< providerEvents.size(); i++){
                events.add(new ExtendedWeekViewEvent(providerEvents.get(i),i));
            }

            int i = 0;
            int d = i + 1;
        }
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



    private void initCalendarWidget(){

        // Get a reference for the week view in the layout.
        calendarWeekView = (WeekView) findViewById(R.id.calander_weekView_widget);

        calendarWeekView.setDateTimeInterpreter(new CalendarDateTimeInterpreter(currentNumOfDays));

        //LISTENERS...

        // to get a callback when an event is pressed
        calendarWeekView.setOnEventClickListener(new CalendarEventClickListener(this));
        // to provide events to the calendar by months
        calendarWeekView.setMonthChangeListener(new CalendarMonthChangeListener(this, events));
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
        ExtendedWeekViewEvent selectedEvent = getEvent(eventId);

        // Check if we are in horizontal mode and if yes show dual pane layout
        if (dualPane) {

            //get existing fragment from frame
            EventDetailsFragment eventDetailsFragment = (EventDetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frame_event_details);


            //if it doesnt exist or it isnt same as fragment already in there then...
            if (eventDetailsFragment == null || eventDetailsFragment.getEvent() == null || eventDetailsFragment.getEventId() != lastSelectedEventId) {


                // Make new fragment with selected data
                eventDetailsFragment = EventDetailsFragment.newInstance(selectedEvent);

                // replace old fragment with new fragment
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_event_details, eventDetailsFragment);
                ft.commit();
            }

        } else {

            // Launch a new Activity to show our DetailsFragment
            Intent intent = new Intent();
            intent.setClass(CalendarActivity.this, EventDetailsActivity.class);

            intent.putExtra(EventDetailsFragment.SELECTED_EVENT_DATA, getEvent(eventId));

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


    public ExtendedWeekViewEvent getEvent(int eventId){
        for(ExtendedWeekViewEvent ewve : events){
            if(ewve.getId() == eventId){
                return ewve;
            }
        }
        return null;
    }

    //TODO write code that updates event data
    public void onEventDetailsUpdate(ExtendedWeekViewEvent selectedEvent) {

        Toast.makeText(getApplicationContext(), "todo update", Toast.LENGTH_SHORT).show();

        //... save data then re-get single event?

        //get existing fragment from frame
        EventDetailsFragment eventDetailsFragment = (EventDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.frame_event_details);

        //update UI and current local event data
        if (eventDetailsFragment != null) {
            eventDetailsFragment.getArguments().putParcelable(EventDetailsFragment.SELECTED_EVENT_DATA, selectedEvent);
            eventDetailsFragment.updateEventDetailsUI();
        }
    }

    /******  Uncomment this and the comment below to see an example of how to use the loader  ********/
    // private List<Event> testList;
    /*************************************************************************************************/

    public Loader<List<Event>> onCreateLoader(int id, Bundle args) {
        return new CalendarLoader(getApplicationContext());    // Obviously cannot really return null, must return a loader
    }

    public void onLoadFinished(Loader<List<Event>> loader, List<Event> eventsData) {
        // Do something with the eventsData

        /*****  Uncomment this and the comment above to see an example of how to use the loader  *****/
        // testList = eventsData;
        // Log.d("The eventList:", testList.toString());
        /*********************************************************************************************/

    }

    public void onLoaderReset(Loader<List<Event>> loader) {
        // Nothing to do
    }
}
