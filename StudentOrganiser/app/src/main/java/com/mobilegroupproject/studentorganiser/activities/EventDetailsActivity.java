package com.mobilegroupproject.studentorganiser.activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;

public class EventDetailsActivity extends AppCompatActivity {

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
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }

    }
}
