package com.mobilegroupproject.studentorganiser.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by alex on 12/05/2016.
 */

public class CalendarLoader extends AsyncTaskLoader<List<Event>> {

    private List<Event> eventData;
    private CalendarProvider calendarProvider = new CalendarProvider(getContext());

    public CalendarLoader(Context ctx) {    // Retrieve current context
        super(ctx);
    }

    @Override
    public List<Event> loadInBackground() {
        eventData = calendarProvider.getAllEvents(calendarProvider.getCalendarDetails());
            // Get all of the event data from the Calendar Provider
        return eventData;
    }

    @Override
    public void deliverResult(List<Event> eventData) {
        if (isStarted()) {
            super.deliverResult(eventData); // Deliver the event data
        }
    }

    @Override
    protected void onStartLoading() {
        if (eventData != null) {
            deliverResult(eventData); // Immediately deliver previously loaded data
        }
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();   // Stop the current load if it exists
    }

    @Override
    protected void onReset() {
        onStopLoading();    // Force the loader to stop
        if (eventData != null) {   // Release the previous data
            eventData = null;
        }
    }

    @Override
    public void onCanceled(List<Event> data) {
        super.onCanceled(data);     // Immediately cancel the load
    }
}