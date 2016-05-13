package com.mobilegroupproject.studentorganiser.fragments;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.mobilegroupproject.studentorganiser.R;

import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    public static final String SELECTED_EVENT_DATA = "selectedEventData";
    private OnFragmentInteractionListener mListener;


    private TextView geoSignTextView;
    private Button geoSignButton;
    private EditText personalCommentaryEditText;
    private Button updatePcButton;
    private ProgressDialog locationProgressDialog;

    //...
    private Boolean geoOrIntent = true;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    protected Location lastKnownLocation;

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
        //prep location services api client
        buildGoogleApiClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ExtendedWeekViewEvent selectedEvent = getEvent();
        Log.d("EVENT", selectedEvent.getName() + " :: " + selectedEvent.getStrId());

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

                    //start location service
                    locationProgressDialog.show();
                    geoOrIntent = true;
                    mGoogleApiClient.connect();
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

                    //start location service
                    locationProgressDialog.show();
                    geoOrIntent = false;
                    mGoogleApiClient.connect();
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

        initLoadingProgressDialog();

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

    private void initLoadingProgressDialog() {
        locationProgressDialog = new ProgressDialog(getActivity());
        locationProgressDialog.setMessage("Getting last known location...");
    }

    protected void createIntentToGoogleMaps() {

        ExtendedWeekViewEvent selectedEvent = getEvent();

        if (lastKnownLocation != null) {
            String startLocation = lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude(); //TODO

            String destinationLocation = selectedEvent.getLat() + "," + selectedEvent.getLng();

            String uri = "http://maps.google.com/maps?saddr=" + startLocation + "&daddr=" + destinationLocation + "&dirflg=w";

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

            startActivity(intent);
        }
    }


    private void createAlertDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void evaluateGeoSign() {

        ExtendedWeekViewEvent selectedEvent = getEvent();


        double lng2 = lastKnownLocation.getLongitude();
        double lat2 = lastKnownLocation.getLatitude();

        //get distance between
        boolean isWithinDistance = isWitihinDistance(selectedEvent.getLat(), selectedEvent.getLng(), lat2, lng2);
        //check if within time contraints
        boolean canGeoSign = isWithinTimeConstraints(selectedEvent.getStartTime(), selectedEvent.getEndTime());

        if (!isWithinDistance) {
            String text = "Not close enough to location to geo-sign you. Please go to event location and try again.";
            createAlertDialog(text);
        } else if (!canGeoSign) {
            String text = "Can only geo-sign during event or within 5 minutes before";
            createAlertDialog(text);
        } else {
            //update object
            selectedEvent.setGeoSigned(true);
            mListener.onEventDetailsUpdate(selectedEvent);
        }
    }


    //public so that activities can call it when done updating
    public void updateEventDetailsUI() {

        final ExtendedWeekViewEvent selectedEvent = getEvent();

        if (geoSignButton != null && geoSignTextView != null && personalCommentaryEditText != null && updatePcButton != null) {


            //if geoSigned..
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

            Calendar now = Calendar.getInstance();
            //if not signed in and have missed event...
            if (!selectedEvent.getGeoSigned()){
                if (selectedEvent.getEndTime().getTimeInMillis() < now.getTimeInMillis()) {

                    geoSignTextView.setText("you didnt attend this event");
                    geoSignButton.setClickable(false);
                    geoSignButton.setEnabled(false);
                }
            }
        }
    }

    private void onLocationFoundCallback() {
        if (geoOrIntent) {
            evaluateGeoSign();
        } else {
            createIntentToGoogleMaps();
        }
    }

    private static boolean isWitihinDistance(double lat1, double lng1, double lat2, double lng2) {
        double worldRadius = 6371000; //meters

        double latDist = Math.toRadians(lat2 - lat1);
        double lngDist = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDist / 2) * Math.sin(latDist / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lngDist / 2) * Math.sin(lngDist / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (worldRadius * c);

        return (dist > 7000) ? false : true;

    }

    public static boolean isWithinTimeConstraints(Calendar startDate, Calendar endDate) {

        Calendar calNow = Calendar.getInstance();
        long now = calNow.getTimeInMillis();

        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();

        if (now >= start && now < end) {
            return true;
        } else if (now >= start - 600000) { // 10 mins before
            return true;
        } else {
            return false;
        }
    }


    // My location handling methods -----------------------------------------------------//

    /**
     * Builds a GoogleApiClient configuration.  The addApi() method used to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * makes sure google client disconnects when fragment stops
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * when google api clinet connected, check if location permission granted,
     * gets last known location and uses this to start a new request and map marker(s) draw
     *
     * @param connectionHint
     */
    @Override
    public void onConnected(Bundle connectionHint) {


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            locationProgressDialog.cancel();

            if (lastKnownLocation != null) {
                onLocationFoundCallback();
                //disconnect when finished
                mGoogleApiClient.disconnect();
            } else {
                Toast.makeText(getActivity(), "location not found", Toast.LENGTH_LONG).show();
                String text = "Could not find location, Check your internet, network or gps connectivity.";
                createAlertDialog(text);
            }
        } else {
            // Show rationale and request permission.
            //TODO request permission here
            //http://developer.android.com/training/permissions/requesting.html

        }


    }

    /**
     * google api client connection failed handler
     *
     * @param result
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("MainInputFragment", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
        //TODO connection failed message
    }

    /**
     * google api client connection suspended handler
     *
     * @param cause
     */
    @Override
    public void onConnectionSuspended(int cause) {

        Log.i("MainInputFragment", "Connection suspended");
        mGoogleApiClient.connect();
    }



}
