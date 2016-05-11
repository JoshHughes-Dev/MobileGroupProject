package com.mobilegroupproject.studentorganiser.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by niall on 11/05/2016.
 */
public class Event {
    public String id;
    public String title;
    public String startTime;
    public String endTime;
    public String location;
    public String creator;
    public String description;

    public static String convertMilliToTime(String milli) {
        Date date = new Date(Long.parseLong(milli)); //Input your time in milliseconds
        String time = new SimpleDateFormat("hh:mm").format(date);
        return time;
    }
}