package com.mobilegroupproject.studentorganiser.listeners;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.fragments.DaysCalendarFragment;
import com.mobilegroupproject.studentorganiser.fragments.WeeksCalendarFragment;



/**
 * handles navigation drawer items selection.
 */
public class NavDrawerItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    Context context;

    public NavDrawerItemSelectedListener(Context c, DrawerLayout d, FragmentManager fm){
        drawer = d;
        fragmentManager = fm;
        context = c;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_cal_days:
                ToggleFragment(false);
                break;
            case R.id.nav_cal_weeks:
                ToggleFragment(true);
                break;
            case R.id.nav_settings:
                Toast.makeText(context,"will open settings view",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_stats:
                Toast.makeText(context,"will open stats/progression view",Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void ToggleFragment(boolean showWeeks){


        DaysCalendarFragment daysCalendarFragment = (DaysCalendarFragment)
                fragmentManager.findFragmentByTag(context.getString(R.string.days_fragment_tag));
        WeeksCalendarFragment weeksCalendarFragment = (WeeksCalendarFragment)
                fragmentManager.findFragmentByTag(context.getString(R.string.weeks_fragment_tag));


        if(weeksCalendarFragment != null && daysCalendarFragment != null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (showWeeks) {
                fragmentTransaction.hide(daysCalendarFragment);
                fragmentTransaction.show(weeksCalendarFragment);
            } else {
                fragmentTransaction.hide(weeksCalendarFragment);
                fragmentTransaction.show(daysCalendarFragment);
            }

            fragmentTransaction.addToBackStack(null).commit();
        }
    }
}
