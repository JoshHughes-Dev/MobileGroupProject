package com.mobilegroupproject.studentorganiser.activities;

import android.content.res.Configuration;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.os.Bundle;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.fragments.EventDetailsFragment;
import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;

public class EventDetailsActivity extends AppCompatActivity implements EventDetailsFragment.OnFragmentInteractionListener {

    private final static String EVENT_DETAILS_FRAGMENT_TAG = "eventDetailsFragment";
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Check if the device is in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

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
    public void onEventDetailsUpdate(ExtendedWeekViewEvent selectedEvent) {

        Toast.makeText(getApplicationContext(), "todo update", Toast.LENGTH_SHORT).show();

        //... save data then re-get single event?

        //get existing fragment from activity content
        EventDetailsFragment eventDetailsFragment = (EventDetailsFragment)
                getSupportFragmentManager().findFragmentByTag(EVENT_DETAILS_FRAGMENT_TAG);

        //update UI and current local event data
        if (eventDetailsFragment != null) {
            eventDetailsFragment.getArguments().putParcelable(EventDetailsFragment.SELECTED_EVENT_DATA, selectedEvent);
            eventDetailsFragment.updateEventDetailsUI();
        }
    }

    // BEGIN_INCLUDE(get_sap)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.eventdetailsactivity, menu);

        // Retrieve the share menu item
        MenuItem shareItem = menu.findItem(R.id.menu_share);

        // Now get the ShareActionProvider from the item
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    /* handles nav bar popup menu items*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.menu_share:
            {
                //get existing fragment from activity content
                EventDetailsFragment eventDetailsFragment = (EventDetailsFragment)
                        getSupportFragmentManager().findFragmentByTag(EVENT_DETAILS_FRAGMENT_TAG);

                //update UI and current local event data
                if (eventDetailsFragment != null) {
                    ExtendedWeekViewEvent event =  eventDetailsFragment.getArguments().getParcelable(EventDetailsFragment.SELECTED_EVENT_DATA);
                    eventDetailsFragment.updateEventDetailsUI();

                    Intent commentIntent = new Intent(); commentIntent.setAction(Intent.ACTION_SEND);
                    commentIntent.setType("text/plain");
                    commentIntent.putExtra(Intent.EXTRA_TEXT, event.getPersonalCommentary() );
                    if (event.getPersonalCommentary() != null) {
                        if (!event.getPersonalCommentary().equals("")) {
                            startActivity(Intent.createChooser(commentIntent, "Share via"));
                            setShareIntent(commentIntent);
                        }
                    } else {
                        Toast.makeText(this, "Personal commentary is empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
