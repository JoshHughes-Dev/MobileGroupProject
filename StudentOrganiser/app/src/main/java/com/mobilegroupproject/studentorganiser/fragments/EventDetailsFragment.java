package com.mobilegroupproject.studentorganiser.fragments;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;

import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;


import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {


    public static final String SELECTED_EVENT_DATA = "selectedEventData";
    private OnFragmentInteractionListener mListener;



    private TextView geoSignTextView;
    private Button geoSignButton;
    private EditText personalCommentaryEditText;
    private Button updatePcButton;


    public EventDetailsFragment() {

    }

    public static EventDetailsFragment newInstance(ExtendedWeekViewEvent selectedEvent) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_EVENT_DATA, selectedEvent);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        void onEventDetailsUpdate(ExtendedWeekViewEvent selectedEvent);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ExtendedWeekViewEvent selectedEvent = getEvent();
        View view;

        if (selectedEvent == null) {
            view = inflater.inflate(R.layout.fragment_event_details_non, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_event_details, container, false);

            TextView titleTextView = (TextView) view.findViewById(R.id.event_title_textview);
            titleTextView.setText(selectedEvent.getName());

            geoSignTextView = (TextView) view.findViewById(R.id.geo_sign_textview);
            geoSignButton = (Button) view.findViewById(R.id.geo_sign_button);
            geoSignButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedEvent.setGeoSigned(true);//TODO this is just dummy
                    mListener.onEventDetailsUpdate(selectedEvent);
                }
            });


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

            updatePcButton = (Button) view.findViewById(R.id.update_pc_button);
            updatePcButton.setClickable(false);
            updatePcButton.setEnabled(false);
            updatePcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //hide keyboard and clear focus
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(personalCommentaryEditText.getWindowToken(), 0);
                    personalCommentaryEditText.clearFocus();

                    //update data model
                    String newText = personalCommentaryEditText.getText().toString();

                    selectedEvent.setPersonalCommentary(newText);
                    //calls interface listener
                    mListener.onEventDetailsUpdate(selectedEvent);
                }
            });


            personalCommentaryEditText = (EditText) view.findViewById(R.id.personal_commentary_editText);
            personalCommentaryEditText.setText(selectedEvent.getPersonalCommentary());
            personalCommentaryEditText.setFocusable(false);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            personalCommentaryEditText.clearFocus();

            personalCommentaryEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //enables update button
                    updatePcButton.setClickable(true);
                    updatePcButton.setEnabled(true);
                }
            });

            updateEventDetailsUI();
        }

        return view;

    }


    public long getEventId() {
        // Returns the index assigned
        //needed by calendar activity
        return getEvent().getId();
    }

    //gets event from fragment arguments
    public ExtendedWeekViewEvent getEvent() {
        return getArguments().getParcelable(SELECTED_EVENT_DATA);
    }

    private String createEventTimeText(ExtendedWeekViewEvent selectedEvent) {

        Date startTime = selectedEvent.getStartTime().getTime();
        Date endTime = selectedEvent.getEndTime().getTime();

        SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm", Locale.UK);
        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a", Locale.UK);

        return sdf1.format(startTime) + " - " + sdf2.format(endTime);

    }

    private String createEventDateText(ExtendedWeekViewEvent selectedEvent) {
        Date date = selectedEvent.getStartTime().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM", Locale.UK);

        return sdf.format(date);
    }

    protected void createIntentToGoogleMaps() {

        ExtendedWeekViewEvent selectedEvent = getEvent();

        String startLocation = "0,0";

        String destinationLocation = selectedEvent.getLat() + "," + selectedEvent.getLng();

        String uri = "http://maps.google.com/maps?saddr=" + startLocation + "&daddr=" + destinationLocation;

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

        startActivity(intent);
    }

    //public so that activities can call it when done updating
    public void updateEventDetailsUI() {

        final ExtendedWeekViewEvent selectedEvent = getEvent();

        if (geoSignButton != null && geoSignTextView != null && personalCommentaryEditText != null && updatePcButton != null) {

            if (selectedEvent.getGeoSigned()) {
                //set geoSign text
                geoSignTextView.setText("you attended this event!");
                geoSignTextView.setTextColor(getActivity().getResources().getColor(R.color.success));
                //disable geoSign button
                geoSignButton.setClickable(false);
                geoSignButton.setEnabled(false);

                //enable pc features
                personalCommentaryEditText.setFocusableInTouchMode(true);

            }

        }
    }





    // GOOGLE PLAY SERVICE ----------//
    // My location handling methods -----------------------------------------------------//

//
////    @Override
////    public void onClick(View view){
////        mGoogleApiClient.connect();
////    }
//
//
//    /**
//     * Builds a GoogleApiClient configuration.  The addApi() method used to request the LocationServices API.
//     */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//    }
//
//
//
//    /**
//     * when google api clinet connected, check if location permission granted,
//     * gets last known location and uses this to start a new request and map marker(s) draw
//     * @param connectionHint
//     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//            if (mLastLocation != null) {
//                //assign last location to do something
//                Toast.makeText(getActivity(),"location found", Toast.LENGTH_LONG).show();
//
//
//                //disconnect when finished
//                mGoogleApiClient.disconnect();
//            } else {
//                Toast.makeText(getActivity(), "location not found", Toast.LENGTH_LONG).show();
//                //TODO show message with some kind of problem occured
//            }
//        } else {
//            // Show rationale and request permission.
//            //TODO request permission here
//            //http://developer.android.com/training/permissions/requesting.html
//        }
//
//
//    }
//
//    /**
//     * google api client connection failed handler
//     * @param result
//     */
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//
//    }
//
//    /**
//     * google api client connection suspended handler
//     * @param cause
//     */
//    @Override
//    public void onConnectionSuspended(int cause) {
//        mGoogleApiClient.connect();
//    }
//
//
}
