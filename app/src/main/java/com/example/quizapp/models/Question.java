package com.example.quizapp.models;

public class Question {
    String answer;
    String []options=new String[4];
    String question;

    public Question(String answer, String[] options, String question) {
        this.answer = answer;
        this.options = options;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public String[] getOptions() {
        return options;
    }

    public String getQuestion() {
        return question;
    }
}
