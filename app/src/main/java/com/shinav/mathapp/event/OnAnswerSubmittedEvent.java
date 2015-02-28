package com.shinav.mathapp.event;

public class OnAnswerSubmittedEvent {

    private final String questionKey;
    private String answer;

    public OnAnswerSubmittedEvent(String questionKey, String answer) {
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
