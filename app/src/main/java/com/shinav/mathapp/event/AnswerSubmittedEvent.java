package com.shinav.mathapp.event;

public class AnswerSubmittedEvent {

    private String answer;

    public AnswerSubmittedEvent(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

}
