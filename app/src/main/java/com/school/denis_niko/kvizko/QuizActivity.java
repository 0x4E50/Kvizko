package com.school.denis_niko.kvizko;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    int randgen=-1;

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

        final Button NextQustionDisplay =(Button) findViewById(R.id.NextQuestionAnwser);
        InputAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    saveData(v);
                    handled = true;
                }
                return handled;
            }
        });
        NextQustionDisplay.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        niz=randomString(datoteka, fileSize);
                        QuestionDisplay(niz);
                        TextAnswer.setText("");
                    }
                }
        );

        StartQuizButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(empty("QuestionsAnswer.txt"))
                        {
                            Snackbar mysnackbar = Snackbar.make(v,"Datoteka je prazna",Snackbar.LENGTH_LONG);
                            mysnackbar.show();
                        }else {
                            DisplayOutput();
                            datoteka = readFile("QuestionsAnswer.txt");
                            fileSize = readFileSize("QuestionsAnswer.txt");
                            System.out.println("substrig =" + randomString(datoteka, fileSize));
                            niz=randomString(datoteka, fileSize);
                            QuestionDisplay(niz);
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
    public void DisplayOutput()
    {
        EditText InputAnswer = (EditText) findViewById(R.id.answer);
        EditText InputQuestion = (EditText) findViewById(R.id.question);
        Button nexButton = (Button) findViewById(R.id.NextQuestion);
        Button StartQuizButton = (Button) findViewById(R.id.StartQuiz);

        Button AnswerButton = (Button) findViewById(R.id.AnwserAndNext);
        TextView TextQuestion = (TextView) findViewById(R.id.QuestionText);
        TextView LabelQuestion = (TextView) findViewById(R.id.labelQuestion);
        TextView LabelAnwser = (TextView) findViewById(R.id.labelAnwser);
        TextView TextAnwser = (TextView)findViewById(R.id.AnwserText);
        Button NextQustionDisplay =(Button) findViewById(R.id.NextQuestionAnwser);
        InputAnswer.setVisibility(View.GONE);
        InputQuestion.setVisibility(View.GONE);
        nexButton.setVisibility(View.GONE);
        StartQuizButton.setVisibility(View.GONE);

        LabelQuestion.setVisibility(View.VISIBLE);
        TextQuestion.setVisibility(View.VISIBLE);
        LabelAnwser.setVisibility(View.VISIBLE);
        AnswerButton.setVisibility(View.VISIBLE);
        NextQustionDisplay.setVisibility(View.VISIBLE);

        TextAnwser.setVisibility(View.VISIBLE);

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
        do {
            rand = random.nextInt(line);
        }while(rand==randgen);
        randgen=rand;
        System.out.println("random line = "+rand);

        String outputString="data";
        int indexrand=datoteka.indexOf("#"+rand);
        int lenght=datoteka.indexOf("$"+rand);
        outputString=datoteka.substring(indexrand+2,lenght);

        System.out.println("indexrand= "+indexrand);
        System.out.println("lenght= "+lenght);
        System.out.println("out= "+outputString);

        return outputString;
    }

    public void saveData(View view)
    {
        final EditText InputQuestion =(EditText) findViewById(R.id.question);
        final EditText InputAnswer =(EditText) findViewById(R.id.answer);
        String Question = InputQuestion.getText().toString();
        String Answer =  InputAnswer.getText().toString();
        System.out.println("Q= "+Question);
        System.out.println("A= "+Answer);
        System.out.println("Index= "+index);
        if(index==0) { //da ne ponovi vrednosti 0
            index = getIndex();//izkanje indexa od prej
            if(index!=0)
            {
                index=index-48;//zaradi asci tabele odštejemo 48 in dobimo index prejšnega vnosa
                index++;//da nastavimo za nov vnos
            }
        }
        if(Question.length()!=0 && Answer.length()!=0)
        {
            WriteFile(Question,Answer,index);
            System.out.println("index "+index);
            index=index+1;
            InputQuestion.setText("");
            InputAnswer.setText("");
            System.out.println("YES");
            System.out.println("index "+index);
            InputQuestion.requestFocus();
        }
        else
        {
            Toast.makeText(this, "Vprašanje ali odgovor ne smeta biti prazna", Toast.LENGTH_SHORT).show();
            System.out.println("NO");
        }
    }

    //Pisanje datoteke
    private void WriteFile(String Question,String Answer,int index){
        try {
            OutputStreamWriter Writer = new OutputStreamWriter(
                    this.openFileOutput("QuestionsAnswer.txt", Context.MODE_APPEND));
            Writer.write("#"+index + Question + "&" + Answer + "$"+index+'\n');
            Writer.close();
            System.out.println("WQ= "+Question);
            System.out.println("WA= "+Answer);
            System.out.println("WIndex= "+index);
            System.out.println("========= ");
            Toast.makeText(this, "Q&A Added", Toast.LENGTH_SHORT).show();
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
                    System.out.println("In File:" + readString);
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
    //prešteje koliko je vseh vrstic v datoteki
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

    //če je datoteka prazna ali pa da ima samo en zapis
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
    //pridobi zadni zapis v datoteki
    public int getIndex (){
        if(!empty("QuestionsAnswer.txt")) {
            int number = 0;
            datoteka = readFile("QuestionsAnswer.txt");
            int length = datoteka.length()-1;
            char lastInput = datoteka.charAt(length);
            System.out.println("zadni char v datoteki je = " + lastInput);
            return lastInput;
        }
        else {
            return 0;
        }
    }
}

