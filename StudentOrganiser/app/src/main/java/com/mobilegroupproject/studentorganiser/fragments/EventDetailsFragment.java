package com.mobilegroupproject.studentorganiser.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.CalenderUITestData;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;
import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {


    public static final String SELECTED_EVENT_DATA = "selectedEventData";

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(ExtendedWeekViewEvent selectedEvent) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_EVENT_DATA, selectedEvent);
        fragment.setArguments(args);
        return fragment;
    }


    public long getEventId() {
	    // Returns the index assigned
        //needed by calendar activity
        ExtendedWeekViewEvent selectedEvent = getEvent();
        return selectedEvent.getId();
    }

    //gets event from fragment arguments
    public ExtendedWeekViewEvent getEvent(){
        return getArguments().getParcelable(SELECTED_EVENT_DATA);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ExtendedWeekViewEvent selectedEvent = getEvent();
        View view;

        if(selectedEvent == null){
            view = inflater.inflate(R.layout.fragment_event_details_non, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_event_details, container, false);

            TextView titleTextView = (TextView) view.findViewById(R.id.event_title_textview);
            titleTextView.setText(selectedEvent.getName());

            TextView eventDateTextView = (TextView) view.findViewById(R.id.event_date_textView);
            eventDateTextView.setText(createEventDateText(selectedEvent));

            TextView eventTimeTextView = (TextView) view.findViewById(R.id.event_time_textView);
            eventTimeTextView.setText(createEventTimeText(selectedEvent));

            TextView eventLocationTextView = (TextView) view.findViewById(R.id.event_location_textview);
            eventLocationTextView.setText(selectedEvent.getLocation());

            TableRow locationTableRow = (TableRow) view.findViewById(R.id.location_tableRow);
            locationTableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createIntentToGoogleMaps();
                }
            });
        }


        return view;

    }


    private String createEventTimeText(ExtendedWeekViewEvent selectedEvent){

        Date startTime = selectedEvent.getStartTime().getTime();
        Date endTime = selectedEvent.getEndTime().getTime();

        SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm", Locale.UK);
        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a", Locale.UK);

        return sdf1.format(startTime) + " - " + sdf2.format(endTime);

    }

    private String createEventDateText(ExtendedWeekViewEvent selectedEvent){
        Date date = selectedEvent.getStartTime().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM", Locale.UK);

        return sdf.format(date);
    }

    protected void createIntentToGoogleMaps(){

        ExtendedWeekViewEvent selectedEvent = getEvent();

        //TODO use current location not placeholder
        String startLocation = "52.7769062,-1.211218";

        String destinationLocation = selectedEvent.getLat() + "," + selectedEvent.getLng();

        String uri = "http://maps.google.com/maps?saddr="+ startLocation + "&daddr=" + destinationLocation;

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

        startActivity(intent);
    }

}
