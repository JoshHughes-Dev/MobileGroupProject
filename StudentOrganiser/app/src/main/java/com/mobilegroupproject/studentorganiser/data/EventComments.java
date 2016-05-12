package com.mobilegroupproject.studentorganiser.data;

import java.util.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dan on 11/05/2016.
 */
public class EventComments extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    // db name as public as we use it in test later
    public static final String DB_NAME = "UserComments.db";
    public static final String COMMENTS_TABLE = "COMMENTS_TABLE";

    public SQLiteDatabase myDB;

    public EventComments(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        myDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        myDB = db;

        String query = "CREATE TABLE IF NOT EXISTS COMMENTS_TABLE ( EVENTID TEXT PRIMARY KEY," +
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

        db.insert("", null, values);
        db.close();
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

        if (c.moveToFirst()) {
            retComment = (c.getString(c.getColumnIndexOrThrow("COMMENT")));
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        return retComment;
    }

    //returns whether event was attended
    public Boolean RequestAttendance(String eventID) {
        Boolean didAttend;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(COMMENTS_TABLE, new String[] { "EVENTID",
                        "ATTENDANCE" }, "EVENTID" + "=?",
                new String[] { eventID }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        didAttend = Boolean.valueOf(cursor.getString(1));

        return didAttend;
    }
}
