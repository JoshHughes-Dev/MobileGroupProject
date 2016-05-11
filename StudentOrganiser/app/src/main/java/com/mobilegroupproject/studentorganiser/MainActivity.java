package com.mobilegroupproject.studentorganiser;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;
import com.mobilegroupproject.studentorganiser.activities.FetchEventsActivity;
import com.mobilegroupproject.studentorganiser.data.CalendarProvider;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    int REQUEST_PERMISSION_READ_CALENDAR = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            String[] perms = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};
            if (EasyPermissions.hasPermissions(this, perms)) {
                CalendarProvider calProvider = new CalendarProvider(getApplicationContext());

                // Shows start time of Event  in Android Monitor.
                Log.d("EVENTA:",(calProvider.getAllEvents(calProvider.getCalendarDetails())).get(200).startTime);
            }
            else {
                // Do not have permissions, request them now
                EasyPermissions.requestPermissions(this, "This application requires the ability to read Calendar data.",
                        REQUEST_PERMISSION_READ_CALENDAR, perms);
            }



        Button tempCalendarBtn = (Button) findViewById(R.id.temp_calender_button);
        Button tempApiTestBtn = (Button) findViewById(R.id.temp_api_button);

        tempCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartCalendarIntent();

            }
        });
        tempApiTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartApiTestIntent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }

    private void StartApiTestIntent() {
        Intent intent = new Intent(this, FetchEventsActivity.class);
        startActivity(intent);
    }


    private void StartCalendarIntent() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }


}
