package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.quizapp.models.Question;
import com.example.quizapp.models.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView subjectsListView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar progressBar;
    SubjectsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectsListView=(ListView) findViewById(R.id.subjects_list_view);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("quiz");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        get_subjects();

        subjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = adapter.getItem(i).getName();
                Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
                intent.putExtra("subject",subject);
                startActivity(intent);
            }
        });

    }
    void get_subjects(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    ArrayList<Subject> subjects = new ArrayList<>();
                    for(DataSnapshot d:dataSnapshot.getChildren()){
                        String key = d.getKey();
                        String count=String.valueOf(d.getChildrenCount());
                        subjects.add(new Subject(key,count));
                        Log.d("TAG",key+" "+count);
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter= new SubjectsAdapter(subjects);
                    subjectsListView.setAdapter(adapter);
                }catch (Exception e){
                    Log.e("TAG",e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}