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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Niko
 */

public class TimerActivity extends Activity implements View.OnClickListener{

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private Button startB;
    public TextView text;
    private int num;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_INTEGER);
        num = Integer.parseInt(message);   // number input in MainActivity via EditText

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        startB = (Button) this.findViewById(R.id.button);
        startB.setOnClickListener(this);
        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(num * 60000, 1000);           // StartTime & Interval
                                                                            // in ms
        if(num == 1) {
            text.setText(
                String.format(getString(R.string.timer_display_singular), num)
            );
        } else {
            text.setText(
                String.format(getString(R.string.timer_display_plural), num)
            );
        }
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();    // cancel timer
        finish();                   // & stop activity
    }

    @Override
    public void onClick(View v) {
        if(!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText(R.string.timer_stop);
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText(R.string.timer_restart);
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        @Override
        public void onFinish() {
            text.setText(R.string.timer_end_message);
            vib.vibrate(1000);   // vibrates for 1 sec on completion

            try {
                FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_APPEND);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write(Integer.toString(num));
                outputWriter.close();

                //display file saved message
                Toast.makeText(getBaseContext(), "File saved successfully!",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = millisUntilFinished / 60000, seconds = millisUntilFinished / 1000,
            sec_in_min = (seconds - (minutes*60));

            if(sec_in_min < 10) {
                text.setText(
                    String.format(getString(R.string.timer_w_zero), minutes, sec_in_min)
                );
            } else {
                text.setText(
                    String.format(getString(R.string.timer), minutes, sec_in_min)
                );
            }

            try {
                FileInputStream fileIn=openFileInput("mytextfile.txt");
                InputStreamReader InputRead= new InputStreamReader(fileIn);

                char[] inputBuffer= new char[READ_BLOCK_SIZE];
                String s="";
                int charRead;
                int number;

                while ((charRead=InputRead.read(inputBuffer))>0) {

                    // char to string conversion
                    String readstring=String.copyValueOf(inputBuffer,0,charRead);
                    s +=readstring;
                }
                InputRead.close();
                Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
