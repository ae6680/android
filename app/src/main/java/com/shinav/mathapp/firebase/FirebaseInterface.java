package com.shinav.mathapp.firebase;

public final class FirebaseInterface {

    public interface Nodes {
        String QUESTIONS = "questions";
    }

    public interface Question {
        public String ANSWER = "answer";
        String CALCULATOR_ALLOWED = "calculatorAllowed";
        String TITLE = "title";
        String VALUE = "value";
    }
}
