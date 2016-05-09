package com.mobilegroupproject.studentorganiser;

import android.content.Context;

import com.alamkanak.weekview.WeekViewEvent;

import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by joshuahughes on 09/05/2016.
 */
public class CalenderUITestData {

    CalendarActivity context;

    public CalenderUITestData(CalendarActivity c){
        context = c;
    }

    public  ArrayList<WeekViewEvent> CreateTestData(){
        ArrayList<WeekViewEvent> events =  new ArrayList<WeekViewEvent>();

        events.add(createWeekEvent(1, "Advanced Networks", "Room SMB002", 2016,5,9,10,1, R.color.event_color_01));
        events.add(createWeekEvent(2, "Mobile Application", "Room SCH138", 2016,5,10,11,2, R.color.event_color_02));
        events.add(createWeekEvent(3, "Data Mining", "Room U005", 2016,5,10,17,1, R.color.event_color_03));
        events.add(createWeekEvent(4, "Mobile Application", "Room J104", 2016,5,11,9,1,R.color.event_color_02));
        events.add(createWeekEvent(5, "Advanced Networks", "Room N109", 2016,5,12,9,2, R.color.event_color_01));
        events.add(createWeekEvent(6, "Computer Vision", "Room D002", 2016,5,12,11,2, R.color.event_color_04));
        events.add(createWeekEvent(7, "Advanced Networks", "Room N001", 2016,5,12,17,1, R.color.event_color_01));
        events.add(createWeekEvent(8, "Mobile Application", "Room CC012", 2016,5,13,9,1, R.color.event_color_02));
        events.add(createWeekEvent(9, "Computer Vision", "Room 201", 2016,5,13,14,2, R.color.event_color_04));

        return events;

    }

    private WeekViewEvent createWeekEvent(int id, String eventName, String location, int year, int month, int day, int hour, int length, int colourId){
        Calendar startTime = Calendar.getInstance();

        startTime.set(year,(month-1),day);

        startTime.set(Calendar.HOUR_OF_DAY, hour); //10am
        startTime.set(Calendar.MINUTE, 0); //has to go in to set on hour

        startTime.set(Calendar.MONTH, month-1);
        startTime.set(Calendar.YEAR, year);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, length);
        endTime.set(Calendar.MONTH, month-1);


        WeekViewEvent event = new WeekViewEvent(id, eventName, location, startTime, endTime);
        event.setColor(context.getResources().getColor(colourId));

        return event;
    }
}
