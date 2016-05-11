package com.mobilegroupproject.studentorganiser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;
import com.mobilegroupproject.studentorganiser.activities.FetchEventsActivity;
import com.mobilegroupproject.studentorganiser.data.CalendarProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarProvider calProvider = new CalendarProvider(getApplicationContext());

        Log.d("CALENDAR:",calProvider.getCalendarDetails());

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

    private void StartApiTestIntent() {
        Intent intent = new Intent(this, FetchEventsActivity.class);
        startActivity(intent);
    }


    private void StartCalendarIntent() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
