package com.example.denis_niko.projektjanez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_INTEGER = "com.example.denis_niko.projektjanez.INTEGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startTimer(View view) {
        Intent timer_intent = new Intent(this, TimerActivity.class);
        EditText editText = (EditText) findViewById(R.id.message_timer);
        String message = editText.getText().toString();

        if(message.matches("")) {
            // make sure input is not empty
            Toast.makeText(this, "You entered nothing!", Toast.LENGTH_LONG).show();
            return;
        }

        int num = Integer.parseInt(message);   // just used for verification

        if(num <= 60 && num > 0) {
            timer_intent.putExtra(EXTRA_INTEGER, message);
            startActivity(timer_intent);
        } else {
            Toast.makeText(this, "Invalid Input!\nTry again.", Toast.LENGTH_LONG).show();
        }
    }

    public void startStats(View view) {
        Intent stats_intent = new Intent(this, StatsActivity.class);
        startActivity(stats_intent);
    }

}
