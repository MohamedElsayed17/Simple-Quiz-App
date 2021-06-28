package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizapp.models.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends BaseAdapter {
    ArrayList<Subject> subjects = new ArrayList<>();

    public SubjectsAdapter(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Subject getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_item_layout,viewGroup,false);
        }
        TextView subjectName = view.findViewById(R.id.subject_name);
        TextView numberOfQuestions = view.findViewById(R.id.number_of_questions);
        Subject s = getItem(i);
        subjectName.setText(s.getName());
        numberOfQuestions.setText("Number Of Questions: "+s.getNumberOfQuestions());



        return view;
    }
}
