package com.mobilegroupproject.studentorganiser.data;

import java.util.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dan on 11/05/2016.
 */
public class EventComments extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    // db name as public as we use it in test later
    public static final String DB_NAME = "UserComments.db";
    public static final String COMMENTS_TABLE = "COMMENTS_TABLE";

    public SQLiteDatabase myDB;

    public EventComments(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, null, DB_VERSION);
        myDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        myDB = db;

        String query = "CREATE TABLE IF NOT EXISTS COMMENTS_TABLE ( _ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "EVENTID TEXT," +
                "ATTENDANCE BOOL," +
                "COMMENT TEXT );";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS COMMENTS_TABLE");

        onCreate(db);
    }

    public void ClearTable(String table_name){
        myDB.execSQL("DELETE FROM COMMENTS_TABLE");
    }

    //used for geosign-in
    public void SignIn(String newComment, String eventID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("EVENTID", eventID);
        values.put("ATTENDANCE", Boolean.TRUE);
        values.put("COMMENT", "");

        db.insert(COMMENTS_TABLE, null, values);
        Log.d("db", getTableAsString(db,COMMENTS_TABLE));

        db.close();
    }

    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d("db", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

    //set comment on an entry
    public void SetComment(String newComment, String eventID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("COMMENT", newComment);

        // updating row
        db.update(COMMENTS_TABLE, values, "EVENTID" + " = ?",
                new String[] { eventID });
        db.close();
    }

    //returns single comment from passed in event ID
    public String RequestComment(String eventID) {
        String retComment = "";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.query(COMMENTS_TABLE, new String[] { "EVENTID",
                        "COMMENT" }, "EVENTID = ?", new String[] { eventID },
                null, null, null);

        if(c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    int colIndex = c.getColumnIndexOrThrow("COMMENT");
                    String d = c.getString(colIndex);
                    retComment = (d);
                } while (c.moveToNext());
            }
        }
        c.close();

        return retComment;
    }

    //returns whether event was attended
    public Boolean RequestAttendance(String eventID) {
        Boolean didAttend = false;
        SQLiteDatabase db = this.getWritableDatabase();



        Cursor c = db.query(COMMENTS_TABLE, new String[] { "EVENTID",
                        "ATTENDANCE" }, "EVENTID = ?", new String[] { eventID },
                null, null, null);


        if(c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    int colIndex = c.getColumnIndexOrThrow("ATTENDANCE");
                    String d = c.getString(colIndex);
                    didAttend = Boolean.valueOf(d);
                } while (c.moveToNext());
            }
        }
        c.close();

//        if (c != null && !c.isClosed()) {
//            c.close();
//        }

        return didAttend;
    }
}
