package com.mobilegroupproject.studentorganiser.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.CalenderUITestData;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {


    public static final String eventIdFlag = "eventIdFlag";

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(int eventId) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(eventIdFlag, eventId);
        fragment.setArguments(args);
        return fragment;
    }


    public int getEventId() {
	    // Returns the index assigned
        return getArguments().getInt(eventIdFlag);
    }

    //TODO use to get single event model
    protected void getEvent(){
//        CalenderUITestData test = new CalenderUITestData((CalendarActivity)getActivity());
//        ArrayList<WeekViewEvent> testEvents = test.CreateTestData();
//        for(int i = 0; i<testEvents; i++){
//            if(testEvents.get(i).getId() == getEventId())
//        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        TextView textView = (TextView) view.findViewById(R.id.event_details_test_textview);
        textView.setText(Integer.toString(getEventId()));

        return view;

    }

}
