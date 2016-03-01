package com.example.denis_niko.projektjanez;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
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
        countDownTimer = new MyCountDownTimer(num * 60000, 1000);           // StartTime & Interval in ms

        if(num == 1) { text.setText(text.getText() + String.valueOf(num + " minute")); }
        else { text.setText(text.getText() + String.valueOf(num + " minutes")); }
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();    // Cancel timer
        finish();                   // and stop activity
    }

    @Override
    public void onClick(View v) {
        if(!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText("Stop");
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("Restart");
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        @Override
        public void onFinish() {
            text.setText("Time is up!");
            vib.vibrate(1000);   // vibrates for 1 sec on completion
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = millisUntilFinished / 60000, seconds = millisUntilFinished / 1000;
            text.setText(minutes + ":" + (seconds - (minutes*60)));
        }
    }

}
