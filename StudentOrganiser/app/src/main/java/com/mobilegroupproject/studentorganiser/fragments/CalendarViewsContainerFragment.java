package com.mobilegroupproject.studentorganiser.fragments;


import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarViewsContainerFragment extends Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {


    private WeekView calendarWeekView;

    public CalendarViewsContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_views_container, container, false);

        // Get a reference for the week view in the layout.
        calendarWeekView = (WeekView) view.findViewById(R.id.calander_weekView_widget);

        // Show a toast message about the touched event.
        calendarWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        calendarWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        calendarWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        calendarWeekView.setEmptyViewLongPressListener(this);

        //initCalendarFragments(savedInstanceState);


        return view;

    }

    /*
    * Creates new or existing instances of weeks and days calendar fragments and adds to container.
    * hides weeks and shows days by default
    * TODO: PASS CALENDER DATA THROUGH HERE
    * */
//    private void initCalendarFragments(Bundle savedInstanceState){
//
//        // Check that the activity is using the layout version with
//        // the fragment_container FrameLayout
//
//        if(savedInstanceState == null){
//
//            // Create a new Fragment to be placed in the activity layout
//            WeeksCalendarFragment weeksCalendarFragment = WeeksCalendarFragment.newInstance();
//            DaysCalendarFragment daysCalendarFragment = DaysCalendarFragment.newInstance();
//
//            // In case this activity was started with special instructions from an
//            // Intent, pass the Intent's extras to the fragment as arguments
//            // weeksCalendarFragment.setArguments(getIntent().getExtras());
//            // daysCalendarFragment.setArguments(getIntent().getExtras());
//
//            // Add the fragment to the 'fragment_container'
//            getFragmentManager().beginTransaction()
//                    .add(R.id.calendar_fragment_container, weeksCalendarFragment, getString(R.string.weeks_fragment_tag))
//                    .add(R.id.calendar_fragment_container, daysCalendarFragment, getString(R.string.days_fragment_tag))
//                    .hide(weeksCalendarFragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
//
//
//    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        return events;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
    }

    public WeekView getWeekView() {
        return calendarWeekView;
    }
}

