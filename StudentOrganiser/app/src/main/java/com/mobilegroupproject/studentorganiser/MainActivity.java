package com.mobilegroupproject.studentorganiser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobilegroupproject.studentorganiser.activities.CalendarActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button tempCalendarBtn = (Button) findViewById(R.id.temp_calender_button);

        tempCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartCalendarIntent();

            }
        });
    }


    private void StartCalendarIntent(){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
