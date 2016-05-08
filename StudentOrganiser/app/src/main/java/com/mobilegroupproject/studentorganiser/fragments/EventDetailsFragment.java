package com.mobilegroupproject.studentorganiser.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {


    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(String eventName) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putString("eventName", eventName);
        fragment.setArguments(args);
        return fragment;
    }


    public String getEventName() {
	    // Returns the index assigned
        Log.d("EventDetailsFragment", getArguments().getString("eventName"));
        return getArguments().getString("eventName");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        TextView textView = (TextView) view.findViewById(R.id.event_details_test_textview);
        textView.setText(getEventName());

        return view;

    }

}
