package com.mobilegroupproject.studentorganiser.listeners;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;
import com.mobilegroupproject.studentorganiser.fragments.CalendarViewsContainerFragment;


/**
 * handles navigation drawer items selection.
 */
public class NavDrawerItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    CalendarActivity context;

    public NavDrawerItemSelectedListener(CalendarActivity c, DrawerLayout d, FragmentManager fm){
        drawer = d;
        fragmentManager = fm;
        context = c;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        WeekView weekView = (WeekView) context.findViewById(R.id.calander_weekView_widget);

        if(weekView != null){



            switch(id) {
                case R.id.nav_cal_day:

                    context.currentNumOfDays = 1;
                    context.setupDateTimeInterpreter(context.currentNumOfDays);
                    weekView.setNumberOfVisibleDays(context.currentNumOfDays);


                    // Lets change some dimensions to best fit the view.
                    weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()));
                    weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));
                    weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));


                    break;
                case R.id.nav_cal_three_day:

                    context.currentNumOfDays = 3;
                    context.setupDateTimeInterpreter(context.currentNumOfDays);
                    weekView.setNumberOfVisibleDays(context.currentNumOfDays);


                    // Lets change some dimensions to best fit the view.
                    weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()));
                    weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));
                    weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));

                    break;
                case R.id.nav_cal_week:

                    context.currentNumOfDays = 7;
                    context.setupDateTimeInterpreter(context.currentNumOfDays);
                    weekView.setNumberOfVisibleDays(context.currentNumOfDays);

                    // Lets change some dimensions to best fit the view.
                    weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));
                    weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, context.getResources().getDisplayMetrics()));
                    weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, context.getResources().getDisplayMetrics()));
                    break;
            }

            context.setCalendarWeekViewCurrentHour();
        }

        switch(id){
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


}
