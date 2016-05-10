package com.mobilegroupproject.studentorganiser.listeners;

import android.content.Context;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.R;
import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;
import com.mobilegroupproject.studentorganiser.model.ExtendedWeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarMonthChangeListener implements MonthLoader.MonthChangeListener {


    protected List<ExtendedWeekViewEvent> events;

    protected CalendarActivity context;

    public CalendarMonthChangeListener(CalendarActivity c, ArrayList<ExtendedWeekViewEvent> es){
        context = c;
        events = es;
    }

    /**
     * Very important interface, it's the base to load events in the calendar.
     * This method is called three times: once to load the previous month, once to load the next month and once to load the current month.<br/>
     * <strong>That's why you can have three times the same event at the same place if you mess up with the configuration</strong>
     * @param newYear : year of the events required by the view.
     * @param newMonth : month of the events required by the view <br/><strong>1 based (not like JAVA API) --> January = 1 and December = 12</strong>.
     * @return a list of the events happening <strong>during the specified month</strong>.
     */
    @Override
    public List<? extends ExtendedWeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Return only the events that matches newYear and newMonth.
        ArrayList<ExtendedWeekViewEvent> matchedEvents = new ArrayList<ExtendedWeekViewEvent>();
        for (ExtendedWeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;

    }


    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year &&
                event.getStartTime().get(Calendar.MONTH) == month - 1) ||
                (event.getEndTime().get(Calendar.YEAR) == year &&
                        event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

}
