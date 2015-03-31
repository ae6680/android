package com.shinav.mathapp.event;

public class SeeQuestionButtonClicked {

    private final String questionFirebaseKey;

    public SeeQuestionButtonClicked(String questionFirebaseKey) {
        this.questionFirebaseKey = questionFirebaseKey;
    }

    public String getQuestionFirebaseKey() {
        return questionFirebaseKey;
    }
}
