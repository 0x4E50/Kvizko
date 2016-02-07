package com.example.denis_niko.projektjanez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Niko
 */

public class TimerActivity extends Activity implements View.OnClickListener{

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private Button startB;
    public TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_INTEGER);
        int num = Integer.parseInt(message);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        startB = (Button) this.findViewById(R.id.button);
        startB.setOnClickListener(this);
        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(num * 1000, 1000);     // StartTime, Interval
        text.setText(text.getText() + String.valueOf(num / 60));     // StartTime / Interval
    }

    @Override
    public void onClick(View v) {
        if(!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText("STOP");
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("RESTART");
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = millisUntilFinished / 60000, seconds = millisUntilFinished / 1000;
            text.setText(minutes + " minutes\n" + (seconds - (minutes*60)) + " seconds");
        }
    }

}
