package com.mobilegroupproject.studentorganiser.data;

import android.util.Log;

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
    public String personalComment;
    public String latitude;
    public String longitude;


    public static String convertMilliToTime(String milli) {
        Date date = new Date(Long.parseLong(milli)); //Input your time in milliseconds
        String time = new SimpleDateFormat("HH:mm").format(date);
        return time;
    }

    public static String convertMilliToDate(String milli) {
        Date dateTemp = new Date(Long.parseLong(milli)); // Input your date in milliseconds
        String date = new SimpleDateFormat("dd:MM:yyyy").format(dateTemp);
        return date;
    }

    public static String[] getLatLngFromBuilding(String location) {

        String[] coords = new String[]{"0","0"};

        //retrieve building code in string form
        String buildingCode = location;
        int commaIndex = buildingCode.indexOf(",");
        if (commaIndex != -1)
        {
            buildingCode = buildingCode.substring(0, commaIndex);
        }
        buildingCode = buildingCode.replaceAll("Room ", "");
        buildingCode = buildingCode.replaceAll("\\d","");



        //if no building code string then return nada.
        if(buildingCode.length() < 1){
            return coords;
        }

        GeoLoc.buildingCode buildingCodeEnum = GeoLoc.buildingCode.valueOf(buildingCode);

        if(buildingCodeEnum != null){
            GeoLoc geoLoc = new GeoLoc();
            String coordsStr = geoLoc.getGeoCoords(buildingCodeEnum);
            coords = coordsStr.split(",");
        }

        return coords;

    }

    //string array for color of events
    public static String[] hexColors = {
            "#59dbe0",
            "#f57f68",
            "#87d288",
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