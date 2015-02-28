package com.shinav.mathapp.event;

public class OnAnswerChangedEvent {

    private final String questionKey;
    private String answer;

    public OnAnswerChangedEvent(String questionKey, String answer) {
        this.questionKey = questionKey;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestionKey() {
        return questionKey;
    }

}
