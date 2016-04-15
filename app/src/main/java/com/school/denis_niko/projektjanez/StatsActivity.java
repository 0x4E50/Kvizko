package com.school.denis_niko.projektjanez;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Niko
 */

public class StatsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String readData = readFile("stats.txt");
        int totalTime = getTotalTime(readData);
        float avgTime = getAvgTime(readData);

        TextView text = (TextView) findViewById(R.id.totalTimeValue);
        text.setText(String.format(getString(R.string.total_time_display), totalTime));

        text = (TextView) findViewById(R.id.avgTimeValue);
        text.setText(String.format(getString(R.string.avg_time_display), avgTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stats_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(deleteFile("stats.txt"))
            Toast.makeText(this, "Stats reset", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Operation failed", Toast.LENGTH_SHORT).show();

        return id == R.id.stats_settings || super.onOptionsItemSelected(item);
    }

    public boolean deleteFile(String fileName) {
        File dir = getFilesDir();
        File file = new File(dir, fileName);
        return file.delete();
    }

    private String readFile(String fileName) {
        String readString = "";

        try {
            InputStream inputStream = openFileInput(fileName);

            if(inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((readString = bufferedReader.readLine()) != null)
                    stringBuilder.append(readString);

                inputStream.close();
                readString = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Cannot read file: " + e.toString());
        }

        return readString;
    }

    private int getTotalTime(String data) {
        // gets total time the timer has run for
        char[] times = data.toCharArray();
        int totalTime = 0;

        for(int i = 0; i < times.length; i++) {
            if(times[i] == '/') {
                int j = i+1;
                String temp = "";
                while(times[j] != ' ') {
                    temp += String.valueOf(times[j]);
                    j++;
                }
                i = j;
                totalTime += Integer.parseInt(temp);
            }
        }

        return totalTime;
    }

    private float getAvgTime(String data) {
        // gets the total avg time
        int numberOfData = 0;
        float avgTime;

        for(int i = 0; i < data.length(); i++) {
            if(data.charAt(i) == '/')
                numberOfData++;
        }

        if(numberOfData == 0)
            avgTime = 0;
        else
            avgTime = (float)getTotalTime(data)/numberOfData;

        return avgTime;
    }

}
