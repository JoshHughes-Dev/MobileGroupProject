package com.mobilegroupproject.studentorganiser.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilegroupproject.studentorganiser.R;


public class WeeksCalendarFragment extends Fragment {


    public WeeksCalendarFragment() {
        // Required empty public constructor
    }

    public static WeeksCalendarFragment newInstance() {
        WeeksCalendarFragment fragment = new WeeksCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weeks_calendar, container, false);
    }

}
