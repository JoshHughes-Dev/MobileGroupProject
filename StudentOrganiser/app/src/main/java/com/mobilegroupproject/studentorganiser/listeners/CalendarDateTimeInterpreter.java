package com.mobilegroupproject.studentorganiser.listeners;

import com.alamkanak.weekview.DateTimeInterpreter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by joshuahughes on 09/05/2016.
 */
public class CalendarDateTimeInterpreter implements DateTimeInterpreter {


    protected int currentNumOfDays = 1;

    public CalendarDateTimeInterpreter(int cnd){
        currentNumOfDays = cnd;
    }

    @Override
    public String interpretDate(Calendar date) {


        String weekdayStringFormat = "EEEE";
        String dateStringFormat = "d MMM";

        switch(currentNumOfDays){
            case 1:
                //use defaults
                break;
            case 3:
                weekdayStringFormat = "EEE";
                dateStringFormat = "d/M";
                break;
            case 7:
                weekdayStringFormat = "EEEEE";
                dateStringFormat = "d/M";
                break;
        }

        SimpleDateFormat weekdayNameFormat = new SimpleDateFormat(weekdayStringFormat, Locale.UK);
        String weekday = weekdayNameFormat.format(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat(dateStringFormat, Locale.UK);


        return weekday.toUpperCase() + " " + format.format(date.getTime());

    }

    @Override
    public String interpretTime(int hour) {
        return hour > 11 ? (hour == 12 ? "12 PM" : (hour - 12) + " PM") : (hour == 0 ? "12 AM" : hour + " AM");
    }
}
