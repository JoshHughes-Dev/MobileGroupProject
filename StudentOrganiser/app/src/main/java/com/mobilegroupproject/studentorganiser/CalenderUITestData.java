package com.mobilegroupproject.studentorganiser;

import android.content.Context;
import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;

import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;
import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;

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

    public  ArrayList<ExtendedWeekViewEvent> CreateTestData(){
        ArrayList<ExtendedWeekViewEvent> events =  new ArrayList<ExtendedWeekViewEvent>();

        events.add(createWeekEvent(1, "Advanced Networks", "Room SMB002", 2016,5,9,10,1, "#59dbe0",false, 52.764939, -1.227303));
        events.add(createWeekEvent(2, "Mobile Application", "Room SCH138", 2016,5,10,11,2, "#f57f68",false, 52.764939, -1.227303));
        events.add(createWeekEvent(3, "Data Mining", "Room U005", 2016,5,10,17,1, "#87d288",true, 52.764939, -1.227303));
        events.add(createWeekEvent(4, "Mobile Application", "Room J104", 2016,5,11,9,1,"#f57f68",true, 52.764939, -1.227303));
        events.add(createWeekEvent(5, "Advanced Networks", "Room N109", 2016,5,12,9,2, "#59dbe0",true, 52.764939, -1.227303));
        events.add(createWeekEvent(6, "Computer Vision", "Room D002", 2016,5,12,11,2, "#f8b552",true, 52.764939, -1.227303));
        events.add(createWeekEvent(7, "Data Mining", "Room N001", 2016,5,12,17,1, "#87d288",false, 52.764939, -1.227303));
        events.add(createWeekEvent(8, "Mobile Application", "Room CC012", 2016,5,13,9,1, "#f57f68",false, 52.764939, -1.227303));
        events.add(createWeekEvent(9, "Computer Vision", "Room 201", 2016,5,13,14,2, "#f8b552",false, 52.764939, -1.227303));

        return events;

    }

    private ExtendedWeekViewEvent createWeekEvent(int id, String eventName, String location, int year, int month, int day, int hour, int length, String colour, Boolean geoSign, double lat, double lng){
        Calendar startTime = Calendar.getInstance();

        startTime.set(year,(month-1),day);

        startTime.set(Calendar.HOUR_OF_DAY, hour); //10am
        startTime.set(Calendar.MINUTE, 0); //has to go in to set on hour

        startTime.set(Calendar.MONTH, month-1);
        startTime.set(Calendar.YEAR, year);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, length);
        endTime.set(Calendar.MONTH, month-1);


        ExtendedWeekViewEvent event = new ExtendedWeekViewEvent(id, eventName, location, startTime, endTime,geoSign,lat,lng);

        event.setColor(Color.parseColor(colour));

        return event;
    }
}
