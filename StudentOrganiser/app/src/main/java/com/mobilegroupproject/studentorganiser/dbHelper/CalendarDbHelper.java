package com.mobilegroupproject.studentorganiser.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import com.mobilegroupproject.studentorganiser.model.Events;
import com.mobilegroupproject.studentorganiser.table.EventsTable;

import static java.util.Arrays.asList;

public class CalendarDbHelper extends SQLiteOpenHelper {
    public CalendarDbHelper(Context context) {
        super(context, "Calendar", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EventsTable.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int prevVersion, int newVersion) {
        sqLiteDatabase.execSQL(EventsTable.DROP_QUERY);
        sqLiteDatabase.execSQL(EventsTable.CREATE_QUERY);
    }

    public Cursor getEventsCursor() {
        return this.getWritableDatabase().rawQuery(EventsTable.SELECT_QUERY, null);
    }
}
