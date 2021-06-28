package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.models.Question;
import com.example.quizapp.models.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView currentQuestionText;
    TextView questionText;
    RadioGroup choicesRadioGroup;
    Button nextButton;
    RadioButton choice1;
    RadioButton choice2;
    RadioButton choice3;
    RadioButton choice4;

    ArrayList<Question> questionArrayList = new ArrayList<>();
    ArrayList<Boolean> userAnswers=new ArrayList<>();
    int currentQuestion=0;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        init_views();

         nextButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(currentQuestion<questionArrayList.size()-1) {
                     int selectedId = choicesRadioGroup.getCheckedRadioButtonId();
                     RadioButton radioButton = (RadioButton) findViewById(selectedId);
                     Boolean checkAnswer = radioButton.getText().toString().equals(questionArrayList.get(currentQuestion).getAnswer());
                     userAnswers.set(currentQuestion,checkAnswer);
                     currentQuestion++;
                     choicesRadioGroup.clearCheck();
                    /* choice1.setChecked(false);
                     choice2.setChecked(false);
                     choice3.setChecked(false);
                     choice4.setChecked(false);*/
                     display_question();

                 }else  if(currentQuestion == questionArrayList.size()-1){
                     int selectedId = choicesRadioGroup.getCheckedRadioButtonId();
                     RadioButton radioButton = (RadioButton) findViewById(selectedId);
                     Boolean checkAnswer = radioButton.getText().toString().equals(questionArrayList.get(currentQuestion).getAnswer());
                     userAnswers.set(currentQuestion,checkAnswer);
                     nextButton.setText("submit");
                     currentQuestion++;
                 }else{
                     int correctAns=0;
                     for(Boolean ans:userAnswers){
                         if(ans)
                            correctAns++;
                         Log.v("ans",ans+"");
                     }
                     Log.v("final",correctAns+"");
                     Intent intent = new Intent(getApplicationContext(),FinalResult.class);
                     intent.putExtra("score",correctAns+"");
                     startActivity(intent);
                 }
             }
         });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String subject = getIntent().getStringExtra("subject");
        Log.v("Quiz",subject);
        database= FirebaseDatabase.getInstance();
        myRef = database.getReference().child("quiz").child(subject);
        get_questions_list();

    }

    void init_views(){
        currentQuestionText = findViewById(R.id.quiz_current_question_text);
        questionText = findViewById(R.id.quiz_question_text);
        choicesRadioGroup = findViewById(R.id.quiz_choices_radio_group);
        nextButton = findViewById(R.id.quiz_next_button);
        choice1 = findViewById(R.id.quiz_choice_1);
        choice2 = findViewById(R.id.quiz_choice_2);
        choice3 = findViewById(R.id.quiz_choice_3);
        choice4 = findViewById(R.id.quiz_choice_4);
    }

    void display_question(){
            currentQuestionText.setText("Question: "+(currentQuestion+1) + "/" + questionArrayList.size());
            Question q = questionArrayList.get(currentQuestion);
            questionText.setText(q.getQuestion());
            choice1.setText(q.getOptions()[0]);
            choice2.setText(q.getOptions()[1]);
            choice3.setText(q.getOptions()[2]);
            choice4.setText(q.getOptions()[3]);

    }

    void get_questions_list(){
        final ProgressDialog dialog = ProgressDialog.show(this,"","Loading...",true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    //Log.v("Quiz",dataSnapshot.getValue().toString());
                    for(DataSnapshot d:dataSnapshot.getChildren()){
                        // Question q =  d.getValue(Question.class);
                        String answer= d.child("answer").getValue(String.class);
                        String options[]=new String[4];
                        int i=0;
                        for(DataSnapshot option:d.child("options").getChildren()){
                            //Log.v("Quiz",option.getKey()+" "+option.getValue());
                            String value=option.getValue(String.class);
                            options[i++]=value;
                        }
                        String question= d.child("question").getValue(String.class);
                        //Log.v("Quiz",answer+" "+options[0]+" "+" "+question);
                        questionArrayList.add(new Question(answer,options,question));
                    }
                    for(int i=0;i<questionArrayList.size();i++){
                        userAnswers.add(false);
                    }
                    display_question();
                    dialog.dismiss();

                }catch (Exception e){
                    Log.e("Quiz",e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

}