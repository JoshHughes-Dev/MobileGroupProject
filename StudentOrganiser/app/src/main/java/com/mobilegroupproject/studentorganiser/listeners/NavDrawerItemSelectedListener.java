package com.mobilegroupproject.studentorganiser.listeners;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;


/**
 * handles navigation drawer items selection.
 */
public class NavDrawerItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    CalendarActivity context;

    public NavDrawerItemSelectedListener(CalendarActivity c, DrawerLayout d){
        drawer = d;
        context = c;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        WeekView weekView = (WeekView) context.findViewById(R.id.calander_weekView_widget);
        int columnGap = 8;
        int textSize = 12;
        int eventTextSize = 12;

        if(weekView != null){
            //handles calender view item selection
            switch(id) {
                case R.id.nav_cal_day:
                    context.currentNumOfDays = 1;
                    columnGap = 8;
                    textSize = 12;
                    eventTextSize = 12;
                    break;

                case R.id.nav_cal_three_day:
                    context.currentNumOfDays = 3;
                    columnGap = 8;
                    textSize = 12;
                    eventTextSize = 12;
                    break;

                case R.id.nav_cal_week:
                    context.currentNumOfDays = 7;
                    columnGap = 2;
                    textSize = 10;
                    eventTextSize = 10;
                    break;
            }

            weekView.setDateTimeInterpreter(new CalendarDateTimeInterpreter(context.currentNumOfDays));
            weekView.setNumberOfVisibleDays(context.currentNumOfDays);

            //change some dimensions to best fit the view.
            weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, columnGap, context.getResources().getDisplayMetrics()));
            weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, context.getResources().getDisplayMetrics()));
            weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, eventTextSize, context.getResources().getDisplayMetrics()));
            //update calender with date and time
            context.setCalendarWeekViewDateAndTime();
        }

        //handles other items
        switch(id){
            case R.id.nav_settings:
                Toast.makeText(context,"will open settings view",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_stats:
                Toast.makeText(context,"will open stats/progress view",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(context,"will open share view",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(context,"will logout user",Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
