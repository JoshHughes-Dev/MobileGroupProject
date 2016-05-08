package com.mobilegroupproject.studentorganiser.listeners;

import android.graphics.RectF;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;

/**
 * Created by joshuahughes on 08/05/2016.
 */
public class CalendarEventClickListener implements WeekView.EventClickListener {


    CalendarActivity context;

    public CalendarEventClickListener(CalendarActivity c){
        context = c;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(context, event.getName(), Toast.LENGTH_SHORT).show();
        context.showEventDetails(event.getName());
    }
}
