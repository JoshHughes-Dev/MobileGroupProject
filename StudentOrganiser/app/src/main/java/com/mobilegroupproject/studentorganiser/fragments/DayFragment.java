package com.mobilegroupproject.studentorganiser.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {


    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_day, container, false);

        Bundle bundle = getArguments();
        String dayName = bundle.getString("data");

        TextView textview = (TextView) view.findViewById(R.id.day_fragement_name);
        textview.setText(dayName);

        return view;
    }

}
