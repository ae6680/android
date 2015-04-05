package com.shinav.mathapp.event;

public class OnAnswerSubmittedEvent {

    private String answer;

    public OnAnswerSubmittedEvent(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

}
