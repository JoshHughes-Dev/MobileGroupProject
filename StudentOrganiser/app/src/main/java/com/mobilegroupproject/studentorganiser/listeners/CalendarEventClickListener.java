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
        //TODO use event id
        context.showEventDetails((int)event.getId());
    }
}
