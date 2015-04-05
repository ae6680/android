package com.shinav.mathapp.firebase;

public abstract class FirebaseInterface {

    public interface Nodes {
        String QUESTIONS = "questions";
        String STORIES = "stories";
        String CONVERSATIONS = "conversations";
        String APPROACHES = "approaches";
        String APPROACH_PARTS = "approach_parts";
    }

    public interface Question {
        String ANSWER = "answer";
        String TITLE = "title";
        String VALUE = "value";
    }

    public interface Approach {
        String QUESTION_KEY = "question_key";
    }

    public interface ApproachPart {
        String APPROACH_KEY = "approach_key";
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
