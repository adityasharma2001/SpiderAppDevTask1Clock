package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button alarm, timer, stopwatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarm = (Button)findViewById(R.id.alarm);
        timer = (Button)findViewById(R.id.timer);
        stopwatch = (Button)findViewById(R.id.stopwatch);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarm();
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimer();
            }
        });

        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStopwatch();
            }
        });
    }
    public void openAlarm(){
        Intent intent = new Intent(this, alarm.class);
        startActivity(intent);
    }

    public void openTimer(){
        Intent intent = new Intent(this, timer.class);
        startActivity(intent);
    }

    public void openStopwatch(){
        Intent intent = new Intent(this, stopwatch.class);
        startActivity(intent);
    }
}
