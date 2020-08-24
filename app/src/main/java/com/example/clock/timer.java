package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class timer extends AppCompatActivity {

    private EditText editText;
    private Button set, start, reset;
    private TextView time;

    private CountDownTimer countDownTimer;

    private Boolean timerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        editText = (EditText) findViewById(R.id.editText);
        set = (Button) findViewById(R.id.btn_set);
        start = (Button) findViewById(R.id.btn_start);
        reset = (Button) findViewById(R.id.btn_reset);
        time = (TextView) findViewById(R.id.countdown);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if(input.length() == 0){
                    Toast.makeText(timer.this, "Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0){
                    Toast.makeText(timer.this, "Please Enter a Positive Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                editText.setText("");
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetTimer();

            }
        });


    }

    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer(){
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timerRunning = false;
                updateWatchInterface();
            }
        }.start();

        timerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis/1000) / 3600 ;
        int minutes = (int) ((mTimeLeftInMillis/1000) % 3600) / 60 ;
        int seconds = (int) ((mTimeLeftInMillis/1000) % 60 );

        String timeLeftFormatted;
        if (hours>0){
            timeLeftFormatted = String.format(Locale.getDefault(), "%d%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }

        time.setText(timeLeftFormatted);
    }

    private void updateWatchInterface(){
        if(timerRunning){
            editText.setVisibility(View.INVISIBLE);
            set.setVisibility(View.INVISIBLE);

            start.setText("PAUSE");
        }
        else {
            editText.setVisibility(View.VISIBLE);
            set.setVisibility(View.VISIBLE);
            start.setText("START");

            if(mTimeLeftInMillis < 1000){
                start.setVisibility(View.INVISIBLE);
            }
            else {
                start.setVisibility(View.VISIBLE);
            }
            if(mTimeLeftInMillis < mStartTimeInMillis){
                reset.setVisibility(View.VISIBLE);
            }
            else {
                reset.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if(timerRunning){
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if(mTimeLeftInMillis < 0){
                mTimeLeftInMillis = 0;
                timerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            }
            else{
                startTimer();
            }
        }
    }
}
