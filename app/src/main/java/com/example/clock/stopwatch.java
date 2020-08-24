package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class stopwatch extends AppCompatActivity {

    private Chronometer chronometer;
    private Boolean running;
    private long pauseOffset;
    private Button  lap;
    private ListView listView;
    String[] ListElements = new String[] { } ;
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        chronometer = (Chronometer) findViewById(R.id.chronometer);


        lap = (Button) findViewById(R.id.btn_lap);
        listView = (ListView) findViewById(R.id.listview1);

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(stopwatch.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listView.setAdapter(adapter);



        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lap = SystemClock.elapsedRealtime() - chronometer.getBase()+"";
                ListElementsArrayList.add(lap);
                adapter.notifyDataSetChanged();
            }
        });


    }

    public void startChronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;


        }

    }

    public void pauseChronometer(View v){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }

    }

    public void resetChronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }


}
