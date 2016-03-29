package com.school.denis_niko.projektjanez;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Niko
 */

public class TimerActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    private long time;
    private int minutes, seconds, interval = 1000;
    private boolean timerRunning = false;

    private Button startButton;
    private TextView timer;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);

        timer = (TextView) this.findViewById(R.id.timer);
        startButton = (Button) findViewById(R.id.button);

        TimePicker.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutes = timePicker.getCurrentHour();     // using to get minutes
                seconds = timePicker.getCurrentMinute();   // and seconds

                time = (minutes*60000) + (seconds*1000);
                startButton.setVisibility(View.GONE);
                timePicker.setVisibility(View.GONE);

                countDownTimer = new MyCountDownTimer(time, interval);
                timerRunning = true;
                countDownTimer.start();
            }
        };

        startButton.setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        if(timerRunning) {
            countDownTimer.cancel();
            finish();
        } else {
            finish();
        }
    }

    private void writeFile(String data) {
        Date d = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("dMMyyyy");
        String date = formattedDate.format(d);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    this.openFileOutput("stats.txt", Context.MODE_APPEND));
            outputStreamWriter.write(date + "/" + data + " ");
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        @Override
        public void onFinish() {
            timer.setText(R.string.timer_end_message);
            vib.vibrate(1000);   // vibrates for 1 sec on completion
            timerRunning = false;

            writeFile(String.valueOf(minutes));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = millisUntilFinished / 60000, seconds = millisUntilFinished / 1000,
            sec_in_min = (seconds - (minutes*60));

            if(sec_in_min < 10) {
                timer.setText(
                        String.format(getString(R.string.timer_w_zero), minutes, sec_in_min));
            } else {
                timer.setText(
                        String.format(getString(R.string.timer), minutes, sec_in_min));
            }
        }

    }

}
