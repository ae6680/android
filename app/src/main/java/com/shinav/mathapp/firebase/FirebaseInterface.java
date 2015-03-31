package com.shinav.mathapp.firebase;

public abstract class FirebaseInterface {

    public interface Nodes {
        String QUESTIONS = "questions";
        String STORIES = "stories";
        String CONVERSATIONS = "conversations";
    }

    public interface Question {
        String ANSWER = "answer";
        String TITLE = "title";
        String VALUE = "value";
        String APPROACHES = "approaches";
    }

    public interface Approach {
        String VALUE = "value";
        String POSITION = "position";
    }

    public interface StoryPart {
        String POSITION = "position";
        String TYPE = "type";
        String TYPE_KEY = "typeKey";
    }

    public interface ConversationPart {
        String POSITION = "position";
        String DELAY = "delay";
        String TYPING_DURATION = "typingDuration";
        String MESSAGE = "message";
    }

}
