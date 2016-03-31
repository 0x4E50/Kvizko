package com.school.denis_niko.projektjanez;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Denis
 */

public class QuizActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final EditText TextQuestion =(EditText) findViewById(R.id.question);
        //String Question = TextQuestion.getText().toString();

        final EditText TextAnswer =(EditText) findViewById(R.id.answer);
        //String Answer = TextAnswer.getText().toString();

        Button nexButton = (Button)findViewById(R.id.NextQuestion);
        // Event listener
        nexButton.setOnClickListener(
            new Button.OnClickListener(){
                public void onClick(View v){
                    // When the button is clicked
                    String Question = TextQuestion.getText().toString();
                    String Answer = TextAnswer.getText().toString();
                    TextQuestion.setText("");
                    TextAnswer.setText("");
                }
            }
        );

    }
    private void WriteFile(String Question,String Answer){
        try {
            OutputStreamWriter Writer = new OutputStreamWriter(
                    this.openFileOutput("QuestionsAnswer.txt", Context.MODE_APPEND));
            Writer.write(Question + "&" + Answer + "/n");
            Writer.close();
        }
        catch (IOException e){
            Log.e("Exception", "File write failed: " +e.toString());
        }
    }

    /** še testiram **/
    public void startString(View view)
    {
        final EditText TextQuestion =(EditText) findViewById(R.id.question);
        //String Question = TextQuestion.getText().toString();

        final EditText TextAnswer =(EditText) findViewById(R.id.answer);
        //String Answer = TextAnswer.getText().toString();
        String Question = TextQuestion.getText().toString();
        String Answer = TextAnswer.getText().toString();
        TextQuestion.setText("");
        TextAnswer.setText("");
    }

}
