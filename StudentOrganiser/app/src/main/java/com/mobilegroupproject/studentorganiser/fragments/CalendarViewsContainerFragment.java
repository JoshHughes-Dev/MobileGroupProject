package com.mobilegroupproject.studentorganiser.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarViewsContainerFragment extends Fragment {


    public CalendarViewsContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_views_container, container, false);

        initCalendarFragments(savedInstanceState);

        return view;

    }

    /*
    * Creates new or existing instances of weeks and days calendar fragments and adds to container.
    * hides weeks and shows days by default
    * TODO: PASS CALENDER DATA THROUGH HERE
    * */
    private void initCalendarFragments(Bundle savedInstanceState){

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout

        if(savedInstanceState == null){

            // Create a new Fragment to be placed in the activity layout
            WeeksCalendarFragment weeksCalendarFragment = WeeksCalendarFragment.newInstance();
            DaysCalendarFragment daysCalendarFragment = DaysCalendarFragment.newInstance();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // weeksCalendarFragment.setArguments(getIntent().getExtras());
            // daysCalendarFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container'
            getFragmentManager().beginTransaction()
                    .add(R.id.calendar_fragment_container, weeksCalendarFragment, getString(R.string.weeks_fragment_tag))
                    .add(R.id.calendar_fragment_container, daysCalendarFragment, getString(R.string.days_fragment_tag))
                    .hide(weeksCalendarFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }


}

