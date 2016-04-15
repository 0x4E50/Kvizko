package com.school.denis_niko.projektjanez;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
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

/**
 * Created by Denis
 */

public class QuizActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final EditText InputQuestion =(EditText) findViewById(R.id.question);
        //String Question = TextQuestion.getText().toString();
        final EditText InputAnswer =(EditText) findViewById(R.id.answer);
        //String Answer = TextAnswer.getText().toString();
        final Button nexButton = (Button)findViewById(R.id.NextQuestion);


        final TextView TextQuestion = (TextView) findViewById(R.id.QuestionText);
        final TextView TextAnswer = (TextView) findViewById(R.id.AnwserText);
        final Button AnswerButton = (Button)findViewById(R.id.AnwserAndNext);

        // Event listener
        nexButton.setOnClickListener(
            new Button.OnClickListener(){
                public void onClick(View v){
                    String Question = InputQuestion.getText().toString();
                    String Answer = InputAnswer.getText().toString();
                    WriteFile(Question,Answer);
                    // When the button is clicked
                    InputQuestion.setText("");
                    InputAnswer.setText("");
                }
            }
        );

        final Button StartQuizButton =(Button)findViewById(R.id.StartQuiz);
        StartQuizButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        InputAnswer.setVisibility(View.GONE);
                        InputQuestion.setVisibility(View.GONE);
                        nexButton.setVisibility(View.GONE);
                       // StartQuizButton.setVisibility(View.GONE);

                        TextQuestion.setVisibility(View.VISIBLE);
                        AnswerButton.setVisibility(View.VISIBLE);
                    }

                }
        );

        AnswerButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        TextAnswer.setVisibility(View.VISIBLE);
                        TextAnswer.setText(readFile("QuestionsAnswer.txt"));

                    }
                }

        );

    }
    //end of on create
    /*
    private void saveData()
    {
        final EditText InputQuestion =(EditText) findViewById(R.id.question);
        final EditText InputAnswer =(EditText) findViewById(R.id.answer);
        String Question = InputQuestion.getText().toString();
        String Answer = InputAnswer.getText().toString();
     WriteFile(Question,Answer);
    }
    private void textboxClear()
    {

    }
*/
    private void WriteFile(String Question,String Answer){
        try {
            OutputStreamWriter Writer = new OutputStreamWriter(
                    this.openFileOutput("QuestionsAnswer.txt", Context.MODE_APPEND));
            Writer.write(Question + "&" + Answer + "\n");
            Writer.close();
        }
        catch (IOException e){
            Log.e("Exception", "File write failed: " +e.toString());
        }
    }

   private String readFile(String fileName){
       String readString="";
       try{
           InputStream inputStream = openFileInput(fileName);
           if(inputStream != null){
               InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
               BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
               StringBuilder stringBuilder = new StringBuilder();

               while((readString = bufferedReader.readLine())!= null){
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
    public boolean deleteFile(String fileName) {
        File dir = getFilesDir();
        File file = new File(dir, fileName);
        return file.delete();
    }
}
