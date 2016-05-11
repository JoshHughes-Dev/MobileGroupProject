package com.mobilegroupproject.studentorganiser.data;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;
import android.support.v4.content.ContextCompat;

/**
 * Created by niall on 11/05/2016.
 */
public class CalendarProvider {

    private Context context;

    public CalendarProvider(Context context) {
        this.context = context;
    }

    public String getCalendarDetails() {

        String[] projection =
                new String[]{
                        Calendars._ID,
                        Calendars.NAME,
                        Calendars.CALENDAR_DISPLAY_NAME,
                        Calendars.ACCOUNT_NAME,
                        Calendars.ACCOUNT_TYPE,
                        Calendars.OWNER_ACCOUNT
                };

        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?))";

        String[] selectionArgs = new String[] {"A.Smith3-11@student.lboro.ac.uk"};

        String displayName = null;
        long id = 0;

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        else {
            Cursor calCursor = context.getContentResolver().
                    query(Calendars.CONTENT_URI,
                            projection,
                            selection,
                            selectionArgs,
                            null);
            while (calCursor.moveToNext()) {
                    id = calCursor.getLong(0);
                    displayName = calCursor.getString(2);
                    return displayName;
                    // …
            }
            }
        return displayName;
    }

}
