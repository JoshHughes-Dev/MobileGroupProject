package com.mobilegroupproject.studentorganiser.activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;
import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;

public class EventDetailsActivity extends AppCompatActivity implements EventDetailsFragment.OnFragmentInteractionListener {

    private final static String EVENT_DETAILS_FRAGMENT_TAG = "eventDetailsFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the device is in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {

            //Check is device is 'large' layout
            if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {

                // on a large screen device in landscape mode, everything should be shown
                // inline so we dont need this activity.
                finish();
                return;
            }

        }

        setTitle("Event Details");

        // Check if we have any data saved
        if (savedInstanceState == null) {

            // If not then create the DetailsFragment
            EventDetailsFragment details = new EventDetailsFragment();

            // Get the Bundle of key value pairs and assign them to the DetailsFragment
            details.setArguments(getIntent().getExtras());

            // Add the details Fragment
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details, EVENT_DETAILS_FRAGMENT_TAG).commit();
        }

    }

    //TODO write code that updates event data
    public void onEventDetailsUpdate(ExtendedWeekViewEvent selectedEvent){

        Toast.makeText(getApplicationContext(), "todo update", Toast.LENGTH_SHORT).show();

        //... save data then re-get single event?

        //get existing fragment from activity content
        EventDetailsFragment eventDetailsFragment = (EventDetailsFragment)
                getSupportFragmentManager().findFragmentByTag(EVENT_DETAILS_FRAGMENT_TAG);

        //update UI and current local event data
        if(eventDetailsFragment != null){
            eventDetailsFragment.getArguments().putParcelable(EventDetailsFragment.SELECTED_EVENT_DATA, selectedEvent);
            eventDetailsFragment.updateGeoSignUI();
        }
    }
}
