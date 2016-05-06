package com.mobilegroupproject.studentorganiser.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.adapters.ViewPagerAdapter;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaysCalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaysCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaysCalendarFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private OnFragmentInteractionListener mListener;
    private int selectedTabPosition;

    public DaysCalendarFragment() {
        // Required empty public constructor
    }

    public static DaysCalendarFragment newInstance() {
        DaysCalendarFragment fragment = new DaysCalendarFragment();
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
        View view = inflater.inflate(R.layout.fragment_days_calendar, container, false);


        viewPager = (ViewPager) view.findViewById(R.id.calendar_view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.controls_tab_layout);
        setTabEvents();

        addDayFragment("Monday");
        addDayFragment("Tuesday");
        addDayFragment("Wednesday");
        addDayFragment("Thursday");
        addDayFragment("Friday");
        addDayFragment("Saturday");
        addDayFragment("Sunday");

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDaysFragmentInteraction();
    }




    private void addDayFragment(String dayName){

        DayFragment dayFragment = new DayFragment();

        Bundle bundle = new Bundle();
        bundle.putString("data", dayName);
        dayFragment.setArguments(bundle);

        viewPagerAdapter.addFrag(dayFragment, dayName);
        viewPagerAdapter.notifyDataSetChanged();
        if (viewPagerAdapter.getCount() > 0) {
            tabLayout.setupWithViewPager(viewPager);
        }

        setCurrentPagerItem(0);
    }



    private void setTabEvents() {

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
                Log.d("Selected", "Selected " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }
        });
    }

    private void setCurrentPagerItem(int index){
        viewPager.setCurrentItem(index);

        selectedTabPosition = viewPager.getCurrentItem();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(viewPagerAdapter.getTabView(i));
        }
    }
}
