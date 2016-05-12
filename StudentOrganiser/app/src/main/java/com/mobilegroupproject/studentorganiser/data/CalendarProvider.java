package com.mobilegroupproject.studentorganiser.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niall on 11/05/2016.
 */
public class CalendarProvider {

    private Context context;

    public CalendarProvider(Context context) {
        this.context = context;
    }

    public List<Calendar> getCalendarDetails() throws SecurityException {

        Calendar calendar = new Calendar();

        List<Calendar> calendarList = new ArrayList<>();

        String[] projection =
                new String[]{
                        Calendars._ID,
                        Calendars.CALENDAR_DISPLAY_NAME,
                        Calendars.ACCOUNT_NAME,
                        Calendars.OWNER_ACCOUNT
                };

        String selection = "((" + Calendars.ACCOUNT_NAME + " LIKE ?))";

        String[] selectionArgs = new String[] {"%"+"@student.lboro.ac.uk"};

        Cursor calCursor = context.getContentResolver().
                query(Calendars.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null);
        while (calCursor.moveToNext()) {
            calendar = new Calendar();
            calendar.id = calCursor.getString(0);
            calendar.calendarName = calCursor.getString(1);
            calendar.accountName = calCursor.getString(2);
            calendar.ownerAccount = calCursor.getString(3);
            calendarList.add(calendar);
        }

        return calendarList;
    }

    public List<Event> getEvents(String calendarId) throws SecurityException {
        Event event = new Event();

        List<Event> eventList = new ArrayList<>();

        String[] projection =
                new String[]{
                        Events._SYNC_ID,
                        Events.TITLE,
                        Events.DTSTART,
                        Events.DTEND,
                        Events.EVENT_LOCATION,
                        Events.ORGANIZER,
                        Events.DESCRIPTION
                };

        String selection = "((" + Events.CALENDAR_ID + " = ?))";

        String[] selectionArgs = new String[] {calendarId};

        Cursor calCursor = context.getContentResolver().
                query(Events.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null);
        while (calCursor.moveToNext()) {
            event = new Event();
            event.id = calCursor.getString(0);
            event.title = calCursor.getString(1);
            event.date = Event.convertMilliToDate(calCursor.getString(2));
            event.startTime = Event.convertMilliToTime(calCursor.getString(2));
            event.endTime = Event.convertMilliToTime(calCursor.getString(3));
            event.location = calCursor.getString(4);
            event.creator = calCursor.getString(5);
            event.description = calCursor.getString(6);
            event.isSigned = null;

            eventList.add(event);   // Add each newly populated event to the list of events
        }

        return eventList;
    }

    public List<Event> getAllEvents(List<Calendar> calendarList) {
        List<Event> events = new ArrayList<>();

        for (Calendar calendar : calendarList){
            List<Event> calendarEvents = getEvents(calendar.id);

            for (Event event : calendarEvents){
                events.add(event);
            }
        }
        return events;
    }
}
