package com.shinav.mathapp.event;

public class MakeQuestionButtonClicked {

    private final String questionFirebaseKey;

    public MakeQuestionButtonClicked(String questionFirebaseKey) {
        this.questionFirebaseKey = questionFirebaseKey;
    }

    public String getQuestionFirebaseKey() {
        return questionFirebaseKey;
    }

}
