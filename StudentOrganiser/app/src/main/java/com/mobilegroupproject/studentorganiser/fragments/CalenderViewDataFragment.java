package com.mobilegroupproject.studentorganiser.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.R;

import java.util.ArrayList;
import java.util.List;

/**
 * http://developer.android.com/guide/topics/resources/runtime-changes.html#RetainingAnObject
 *
 */
public class CalenderViewDataFragment extends Fragment {


    // data object we want to retain
    private ArrayList<WeekViewEvent> data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(ArrayList<WeekViewEvent> data) {
        this.data = data;
    }

    public ArrayList<WeekViewEvent> getData() {
        return data;
    }

}
