package com.shinav.mathapp.firebase;

public final class FirebaseInterface {

    public interface Nodes {
        String QUESTIONS = "questions";
    }

    public interface Question {
        String ANSWER = "answer";
        String TITLE = "title";
        String VALUE = "value";
        String APPROACHES = "approaches";
    }

    public interface Approaches {
        String VALUE = "value";
        String POSITION = "position";
    }

}
