package com.mobilegroupproject.studentorganiser.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by niall on 11/05/2016.
 */
public class Event {
    public String id;
    public String title;
    public String date;
    public String startTime;
    public String endTime;
    public String location;
    public String creator;
    public String description;
    public String isSigned; // Was the event attended by the student?
    public String hexColor;

    public static String convertMilliToTime(String milli) {
        Date date = new Date(Long.parseLong(milli)); //Input your time in milliseconds
        String time = new SimpleDateFormat("hh:mm").format(date);
        return time;
    }

    public static String convertMilliToDate(String milli) {
        Date dateTemp = new Date(Long.parseLong(milli)); // Input your date in milliseconds
        String date = new SimpleDateFormat("dd:MM:yyyy").format(dateTemp);
        return date;
    }

    //string array for color of events
    public static String[] hexColors = {
            "#59dbe0",
            "#f57f68",
            "#87d288",
            "#f57f68",
            "#59dbe0",
            "#f8b552",
            "#87d288",
            "#f57f68",
            "#f8b552",
            "#cde642",
            "#dc1ac1",
            "#e7a09d",
            "#05ce8c",
            "#91c9db",
            "#64ffae",
            "#16ad48",
            "#fe4600",
            "#f9dda2",
            "#42f6ee",
            "#a51114",
            "#d77232"
    };
}