package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalResult extends AppCompatActivity {

    TextView scoreText;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        init_views();
        String score = getIntent().getStringExtra("score");
        Log.v("final result",score+"");
        if(score!=null && !score.equals(""))
            scoreText.setText(String.valueOf(Integer.parseInt(score)*10));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinalResult.this,MainActivity.class));
            }
        });
    }
    void init_views(){
        scoreText=findViewById(R.id.final_result_score_text);
        nextButton=findViewById(R.id.final_result_next_button);
    }
}