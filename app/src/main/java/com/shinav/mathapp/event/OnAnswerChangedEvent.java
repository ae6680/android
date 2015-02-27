package com.shinav.mathapp.event;

public class OnAnswerChangedEvent {

    private String answer;

    public OnAnswerChangedEvent(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

}
