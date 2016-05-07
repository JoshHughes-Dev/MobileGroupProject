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
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaysCalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaysCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaysCalendarFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private int selectedTabPosition;

    public DaysCalendarFragment() {
        // Required empty public constructor
    }

    /*
    * TODO: Pass current week's calendar events through here
    * */
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

        createDayFragments();


        return view;
    }


    /*
    * TODO: pass single day's calendar events through here
    * */
    private void addDayFragment(Map.Entry<String, Integer> mapEntry){

        DayFragment dayFragment = DayFragment.newInstance(mapEntry);

//        Bundle bundle = new Bundle();
//        bundle.putString("data", dayName);
//        dayFragment.setArguments(bundle);

        viewPagerAdapter.addFrag(dayFragment, mapEntry.getKey());
        viewPagerAdapter.notifyDataSetChanged();
        if (viewPagerAdapter.getCount() > 0) {
            tabLayout.setupWithViewPager(viewPager);
        }

        //setCurrentPagerItem(0);
    }



    private void setTabEvents() {

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
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



    private void createDayFragments(){

        Calendar calendar = GregorianCalendar.getInstance();

        calendar.set(Calendar.MONTH, calendar.getFirstDayOfWeek());
        Map<String,Integer> unsortedMap = calendar.getDisplayNames(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        Map<String,Integer> sortedMap = sortByValue(unsortedMap);

        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            addDayFragment(entry);
        }


        setCurrentPagerItem(getCurrentDayOfWeek());
    }

    /* util method to get current day of week as int (1-7)*/
    private int getCurrentDayOfWeek(){

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day == 1){
            return 7;
        }
        else{
            return day-1;
        }
    }

    /* util method to sort days of week map*/
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> convertedList = new LinkedList<>(map.entrySet());

        Collections.sort(convertedList, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : convertedList)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
