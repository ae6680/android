package com.shinav.mathapp.event;

import com.shinav.mathapp.question.Question;

public class OnAnswerSubmittedEvent {

    private final Question question;
    private String answer;

    public OnAnswerSubmittedEvent(Question question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public Question getQuestion() {
        return question;
    }
    
}
