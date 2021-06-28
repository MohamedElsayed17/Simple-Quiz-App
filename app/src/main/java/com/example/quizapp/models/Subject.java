package com.example.quizapp.models;

public class Subject {
    String name;
    String numberOfQuestions;

    public Subject(String name, String numberOfQuestions) {
        this.name = name;
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }
}
