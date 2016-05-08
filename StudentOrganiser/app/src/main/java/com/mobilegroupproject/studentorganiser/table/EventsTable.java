package com.mobilegroupproject.studentorganiser.table;

import android.provider.BaseColumns;

/**
 * Created by alex on 08/05/2016.
 */
public class EventsTable implements BaseColumns {
    public static final String TITLE = "title";
    public static final String DATETIME = "date_time";
    public static final String BUILDING = "building";
    public static final String HANGOUT_LINK = "hangout_link";
    public static final String CREATOR = "event_creator";
    public static final String COLOUR_ID = "colour_id";
    public static final String DESCRIPTION = "description";
    public static final String UID = "uid";
    public static final String TABLE_NAME = "calendars";

    public static final String CREATE_QUERY = "create table " + TABLE_NAME + " (" +
            _ID + " INTEGER, " +
            TITLE + " TEXT, " +
            DATETIME + " TEXT, " +
            BUILDING + " TEXT, " +
            HANGOUT_LINK + " TEXT, " +
            CREATOR + " TEXT, " +
            COLOUR_ID + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            UID + " TEXT)";

    public static final String DROP_QUERY = "drop table " + TABLE_NAME;
    public static final String SELECT_QUERY = "select * from " + TABLE_NAME;
}
