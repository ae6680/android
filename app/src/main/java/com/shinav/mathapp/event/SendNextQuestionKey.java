package com.shinav.mathapp.event;

public class SendNextQuestionKey {
    private final String nextQuestionKey;

    public SendNextQuestionKey(String nextQuestionKey) {
        this.nextQuestionKey = nextQuestionKey;
    }

    public String getNextQuestionKey() {
        return nextQuestionKey;
    }

}
