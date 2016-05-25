package com.school.denis_niko.kvizko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void startTimer(View view) {
        Intent timer_intent = new Intent(this, TimerActivity.class);
        startActivity(timer_intent);
    }

    public void startQuiz(View view){
        Intent quiz_intent = new Intent(this, QuizActivity.class);
        startActivity(quiz_intent);
    }

    public void startStats(View view) {
        Intent stats_intent = new Intent(this, StatsActivity.class);
        startActivity(stats_intent);
    }

}
