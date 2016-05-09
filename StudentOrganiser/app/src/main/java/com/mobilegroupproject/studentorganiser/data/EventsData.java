package com.mobilegroupproject.studentorganiser.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.api.client.util.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 09/05/2016.
 */
public class EventsData {

    private Context mContext;

    private EventsDbHelper eventsDbHelper;
    private SQLiteDatabase eventsDb;


    public EventsData(Context context){
        mContext = context;
        eventsDbHelper = new EventsDbHelper(mContext,EventsDbHelper.DB_NAME,null,
                com.mobilegroupproject.studentorganiser.data.EventsDbHelper.DB_VERSION);

        eventsDb = eventsDbHelper.getWritableDatabase();
    }

    public List<String> convertDateTime(DateTime startDateTime, DateTime endDateTime) {
        List<String> converted = new ArrayList<>();

        //Get date from startDateTime and add to return variable.
        SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-mm-yyyy");

        //Check for all day and get times from startDateTime, then add to return variable.
        if(startDateTime.toString().length() < 11){ // Checking if startDateTime is a time or a date. A date will be larger then 5 characters and a time won't be.
            Log.d("NOT_TIME", startDateTime.toString());
            //converted.add("DATE AS NO TIME GIVEN.");
            try {
                converted.add(targetFormat.format(apiFormat.parse(startDateTime.toString())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            converted.add("00:00");
            converted.add("23:59");
        }
        else{
            Log.d("YES_TIME", startDateTime.toString().substring(0, 9));
            try {
                converted.add(targetFormat.format(apiFormat.parse(startDateTime.toString().substring(0, 9))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            converted.add(startDateTime.toString().substring(11, 16));
            Log.d("GIMME DAT START TIME", startDateTime.toString().substring(11, 16));
            //converted.add(endDateTime);
            Log.d("GIMME DAT END TIME", endDateTime.toString().substring(11, 16));
            converted.add(endDateTime.toString().substring(11, 16));
        }

        return converted;
    }

    public void pushApiDataToEventsDb(List<List<String>> eventMasterList) {

        eventsDbHelper.clearTable("EVENTS_TABLE");

        ContentValues values = new ContentValues();
        for (int i = 0; i < eventMasterList.size(); i++) {

            values.put("TITLE", eventMasterList.get(i).get(0));
            values.put("DATE", eventMasterList.get(i).get(1));
            values.put("STARTTIME", eventMasterList.get(i).get(2));
            values.put("ENDTIME", eventMasterList.get(i).get(3));
            values.put("BUILDING", eventMasterList.get(i).get(4));
            values.put("HANGOUT_LINK", eventMasterList.get(i).get(5));
            values.put("CREATOR", eventMasterList.get(i).get(6));
            values.put("COLOUR_ID", eventMasterList.get(i).get(7));
            values.put("DESCRIPTION", eventMasterList.get(i).get(8));
            values.put("UID", eventMasterList.get(i).get(9));

            eventsDb.insert("EVENTS_TABLE", null, values);
        }
    }

    public List<List<String>> returnEventsData() {

        List<List<String>> eventsList = new ArrayList<>();

        String[] columns = {
                "TITLE", "DATE", "STARTTIME", "ENDTIME", "BUILDING", "HANGOUT_LINK", "CREATOR",
                "COLOUR_ID", "DESCRIPTION", "UID"
        };

        Cursor c = eventsDb.query(
                "EVENTS_TABLE",
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            //testString = c.getString(c.getColumnIndex("UID"));
            //Log.d("hello",testString);
            do {
                Log.d("hello TITLE",c.getString(c.getColumnIndex("TITLE")));
                Log.d("hello DATE",c.getString(c.getColumnIndex("DATE")));
                Log.d("hello STARTTIME",c.getString(c.getColumnIndex("STARTTIME")));
                Log.d("hello ENDTIME", c.getString(c.getColumnIndex("ENDTIME")));
                Log.d("hello BUILDING",c.getString(c.getColumnIndex("BUILDING")));
                Log.d("hello HANGOUT_LINK",c.getString(c.getColumnIndex("HANGOUT_LINK")));
                Log.d("hello CREATOR",c.getString(c.getColumnIndex("CREATOR")));
                Log.d("hello COLOUR_ID",c.getString(c.getColumnIndex("COLOUR_ID")));
                Log.d("hello DESCRIPTION",c.getString(c.getColumnIndex("DESCRIPTION")));
                Log.d("hello UID",c.getString(c.getColumnIndex("UID")));

                List<String> eventData = new ArrayList<>();
                eventData.add(c.getString(c.getColumnIndex("TITLE")));
                eventData.add(c.getString(c.getColumnIndex("DATE")));
                eventData.add(c.getString(c.getColumnIndex("STARTTIME")));
                eventData.add(c.getString(c.getColumnIndex("ENDTIME")));
                eventData.add(c.getString(c.getColumnIndex("BUILDING")));
                eventData.add(c.getString(c.getColumnIndex("HANGOUT_LINK")));
                eventData.add(c.getString(c.getColumnIndex("CREATOR")));
                eventData.add(c.getString(c.getColumnIndex("COLOUR_ID")));
                eventData.add(c.getString(c.getColumnIndex("DESCRIPTION")));
                eventData.add(c.getString(c.getColumnIndex("UID")));
                eventsList.add(eventData);
            } while (c.moveToNext());
        } else {
            List<String> errorData = new ArrayList<>();
            errorData.add("error TITLE");
            errorData.add("error DATE");
            errorData.add("error STARTTIME");
            errorData.add("error ENDTIME");
            errorData.add("error BUILDING");
            errorData.add("error HANGOUT_LINK");
            errorData.add("error CREATOR");
            errorData.add("error COLOUR_ID");
            errorData.add("error DESCRIPTION");
            errorData.add("error UID");
            eventsList.add(errorData);
        }

        return eventsList;
    }
}
