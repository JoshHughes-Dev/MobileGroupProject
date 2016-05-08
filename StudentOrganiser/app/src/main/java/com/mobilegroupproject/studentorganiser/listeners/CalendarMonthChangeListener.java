package com.mobilegroupproject.studentorganiser.listeners;

import android.content.Context;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by joshuahughes on 08/05/2016.
 */
public class CalendarMonthChangeListener implements MonthLoader.MonthChangeListener {


    protected List<WeekViewEvent> events;

    protected CalendarActivity context;

    public CalendarMonthChangeListener(CalendarActivity c){
        context = c;
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        events = new ArrayList<WeekViewEvent>();


        createWeekEvent(1, "Advanced Networks", "Room SMB002", 2016,5,9,10,1,newYear, newMonth, R.color.event_color_01);
        createWeekEvent(2, "Mobile Application", "Room SCH138", 2016,5,10,11,2,newYear, newMonth, R.color.event_color_02);
        createWeekEvent(3, "Data Mining", "Room U005", 2016,5,10,17,1,newYear, newMonth, R.color.event_color_03);
        createWeekEvent(4, "Mobile Application", "Room J104", 2016,5,11,9,1,newYear, newMonth, R.color.event_color_02);
        createWeekEvent(5, "Advanced Networks", "Room N109", 2016,5,12,9,2,newYear, newMonth, R.color.event_color_01);
        createWeekEvent(6, "Computer Vision", "Room D002", 2016,5,12,11,2,newYear, newMonth, R.color.event_color_04);
        createWeekEvent(7, "Advanced Networks", "Room N001", 2016,5,12,17,1,newYear, newMonth, R.color.event_color_01);
        createWeekEvent(8, "Mobile Application", "Room CC012", 2016,5,13,9,1,newYear, newMonth, R.color.event_color_02);
        createWeekEvent(9, "Computer Vision", "Room 201", 2016,5,13,14,2,newYear, newMonth, R.color.event_color_04);


//        //AllDay event
//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 0);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, newMonth-1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR_OF_DAY, 23);
//        event = new WeekViewEvent(7, getEventTitle(startTime),null, startTime, endTime);
//        event.setColor(context.getResources().getColor(R.color.event_color_04));
//        events.add(event);
//        events.add(event);


        return events;
    }


    private void createWeekEvent(int id, String eventName, String location, int year, int month, int day, int hour, int length, int newYear, int newMonth, int colourId){
        Calendar startTime = Calendar.getInstance();

        startTime.set(year,(month-1),day);

        startTime.set(Calendar.HOUR_OF_DAY, hour); //10am
        startTime.set(Calendar.MINUTE, 0); //has to go in to set on hour

        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, length);
        endTime.set(Calendar.MONTH, newMonth - 1);


        WeekViewEvent event = new WeekViewEvent(id, eventName, location, startTime, endTime);
        event.setColor(context.getResources().getColor(colourId));
        events.add(event);
    }



    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
}
