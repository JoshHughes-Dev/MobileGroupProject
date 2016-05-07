package com.mobilegroupproject.studentorganiser.fragments;


import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.TempEventsData;
import com.mobilegroupproject.studentorganiser.adapters.DayEventsListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {

    private static final String DayString = "DayString";
    private static final String DayDateInt = "DayDateString";

    private String dayString;
    private Integer dayDateInt;

    private OnFragmentInteractionListener mListener;

    public DayFragment() {
        // Required empty public constructor
    }


    /*
    * TODO: Pass single day's calendar events through here
    * */
    public static DayFragment newInstance(Map.Entry<String,Integer> mapEntry) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putString(DayString, mapEntry.getKey());
        args.putInt(DayDateInt, mapEntry.getValue());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayString = getArguments().getString(DayString);
            dayDateInt = getArguments().getInt(DayDateInt);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_day, container, false);

        TextView dayDateTextView = (TextView) view.findViewById(R.id.day_date_textview);

        String titleString = dayString + " " + dayDateInt + getDayOfMonthSuffix(dayDateInt);
        dayDateTextView.setText(titleString);

        createListView(TempEventsData.Events, view);

        return view;
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

    public interface OnFragmentInteractionListener {
        void onDayFragmentEventSelected(int index);
    }


    /* TODO will use to update list events after new entry is added to data set */
    public void updateEventsList(){}


    /* util to get day suffix */
    private String getDayOfMonthSuffix(final int n) {

        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }


    /* initialises events list and binds data
    * TODO put through rela data
    * */
    private void createListView(final String[] tempStringArray, View view){

        ListView dayEventsListView = (ListView) view.findViewById(R.id.fragment_day_events_listview);

        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(tempStringArray));
        DayEventsListAdapter adapter = new DayEventsListAdapter(getActivity(), stringList);

        dayEventsListView.setAdapter(adapter);
        dayEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mListener.onDayFragmentEventSelected(position);
            }
        });
    }


}

