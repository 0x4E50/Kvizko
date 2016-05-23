package com.school.denis_niko.projektjanez;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Created by Denis
 */

public class QuizActivity extends AppCompatActivity {

    int index = 0;
    private String datoteka;
    int fileSize = 0;
    String niz="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //final EditText InputQuestion =(EditText) findViewById(R.id.question);
        //final EditText InputAnswer =(EditText) findViewById(R.id.answer);
        final Button nexButton = (Button) findViewById(R.id.NextQuestion);

        final EditText InputQuestion = (EditText) findViewById(R.id.question);
        final EditText InputAnswer = (EditText) findViewById(R.id.answer);

        final TextView TextQuestion = (TextView) findViewById(R.id.QuestionText);
        final TextView TextAnswer = (TextView) findViewById(R.id.AnwserText);
        final Button AnswerButton = (Button) findViewById(R.id.AnwserAndNext);


        final Button StartQuizButton = (Button) findViewById(R.id.StartQuiz);
        StartQuizButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (InputAnswer.getVisibility() == View.VISIBLE&& !empty("QuestionsAnswer.txt"))
                        {
                            InputAnswer.setVisibility(View.GONE);
                            InputQuestion.setVisibility(View.GONE);
                            nexButton.setVisibility(View.GONE);
                            // StartQuizButton.setVisibility(View.GONE);
                            TextQuestion.setVisibility(View.VISIBLE);
                            AnswerButton.setVisibility(View.VISIBLE);

                            //deleteFile("QuestionsAnswer.txt");
                            StartQuizButton.setText("Next Question");
                            datoteka = readFile("QuestionsAnswer.txt");
                            fileSize = readFileSize("QuestionsAnswer.txt");
                            System.out.println("substrig =" + randomString(datoteka, fileSize));
                            niz=randomString(datoteka, fileSize);
                            QuestionDisplay(niz);
                        }else if(InputAnswer.getVisibility() == View.GONE) //ko gumb spremeni ime
                        {
                            niz=randomString(datoteka, fileSize);
                            QuestionDisplay(niz);
                            TextAnswer.setText("");
                        }else
                        {
                            Snackbar mysnackbar = Snackbar.make(v,"Datoteka je prazna",Snackbar.LENGTH_LONG);
                            mysnackbar.show();
                        }

                    }

                }
        );

        AnswerButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        TextAnswer.setVisibility(View.VISIBLE);
                        QuestionAnwser(niz);

                    }
                }

        );

    }

    //end of on create

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(deleteFile("QuestionsAnswer.txt"))
            Toast.makeText(this, "Data reset", Toast.LENGTH_SHORT).show();
        else if(empty("QuestionsAnswer.txt"))
            Toast.makeText(this, "File is empty", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Operation failed", Toast.LENGTH_SHORT).show();

        return id == R.id.quiz_settings || super.onOptionsItemSelected(item);
    }
    public void QuestionDisplay(String datoteka)
    {
        int endofQ=datoteka.indexOf("&");
        String out= datoteka.substring(0,endofQ);
        TextView TextQuestion = (TextView) findViewById(R.id.QuestionText);
        TextQuestion.setText(out);
    }
    public void QuestionAnwser(String datoteka)
    {
        int startofA=datoteka.indexOf("&");
        String out= datoteka.substring(startofA+1);
        TextView TextAnwser = (TextView) findViewById(R.id.AnwserText);
        TextAnwser.setText(out);
    }
    public String randomString(String datoteka, int line)
    {
     int rand;
        Random random = new Random();
        rand=random.nextInt(line);
        System.out.println("random line = "+rand);

        String outputString="data";
        int index=datoteka.indexOf("#"+rand);
        int lenght=datoteka.indexOf("$"+rand);
        outputString=datoteka.substring(index+2,lenght);

        System.out.println("index= "+index);
        System.out.println("lenght= "+lenght);
        System.out.println("out= "+outputString);

        return outputString;
    }

    public void saveData(View view)
    {
        Snackbar mysnackbar = Snackbar.make(view,"Vprašanje ali odgovor ne smeta biti prazna",Snackbar.LENGTH_LONG);
        final EditText InputQuestion =(EditText) findViewById(R.id.question);
        final EditText InputAnswer =(EditText) findViewById(R.id.answer);
        String Question = InputQuestion.getText().toString();
        String Answer =  InputAnswer.getText().toString();
        System.out.println("Q= "+Question);
        System.out.println("A= "+Answer);
        if(Question.length()!=0 && Answer.length()!=0)
        {
            WriteFile(Question,Answer,index);
            index++;
            InputQuestion.setText("");
            InputAnswer.setText("");
            System.out.println("YES");
        }
        else
        {
            mysnackbar.show();
            System.out.println("NO");
        }
    }
    private void textboxClear()
    {

    }
    //Pisanje datoteke
    private void WriteFile(String Question,String Answer,int index){
        try {
            OutputStreamWriter Writer = new OutputStreamWriter(
                    this.openFileOutput("QuestionsAnswer.txt", Context.MODE_APPEND));
            Writer.write("#"+index + Question + "&" + Answer + "$"+index+'\n');
            Writer.close();
        }
        catch (IOException e){
            Log.e("Exception", "File write failed: " +e.toString());
        }
    }

    //Branje datotek
   private String readFile(String fileName){
       String readString="";
       try{
           InputStream inputStream = openFileInput(fileName);
           if(inputStream != null){
               InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
               BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
               StringBuilder stringBuilder = new StringBuilder();

               while((readString = bufferedReader.readLine())!= null){
                   System.out.println("NEKE:" + readString);
                   stringBuilder.append(readString);
               }
               inputStream.close();
               readString = stringBuilder.toString();
           }
       }catch (FileNotFoundException e){
           Log.e("login activity", "File not found: " + e.toString());
       } catch (IOException e){
           Log.e("login activity", "Can not read file: " + e.toString());
       }
       return readString;
   }

    private int readFileSize(String fileName){
        String readString="";
        int line =0;
        try{
            InputStream inputStream = openFileInput(fileName);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((readString = bufferedReader.readLine())!= null){
                    stringBuilder.append(readString);
                    line++;
                }
                inputStream.close();
                readString = stringBuilder.toString();
            }
        }catch (FileNotFoundException e){
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e){
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        System.out.println("Stevilo vrstic:" + line);
        return line;
    }


    //Brisanje datoteke
    public boolean deleteFile(String fileName) {
        File dir = getFilesDir();
        File file = new File(dir, fileName);
        return file.delete();
    }
    public boolean empty(String fileName)
    {
        String readString="";
        int line =0;
        try{
            InputStream inputStream = openFileInput(fileName);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((readString = bufferedReader.readLine())!= null){
                    stringBuilder.append(readString);
                    line++;
                }
                inputStream.close();
                readString = stringBuilder.toString();
            }
        }catch (FileNotFoundException e){
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e){
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        System.out.println("Stevilo vrstic:" + line);

        if (line==0||line==1)
        {
            return true;
        }else {
            return false;
        }
    }
}

