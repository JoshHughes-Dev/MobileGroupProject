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
        //setContentView(R.layout.activity_event_details);

        // Check if the device is in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        // If the screen is now in landscape mode, we can show the
        // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        // Check if we have any hero data saved
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
