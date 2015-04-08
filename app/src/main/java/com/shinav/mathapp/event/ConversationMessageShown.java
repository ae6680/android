package com.shinav.mathapp.event;

public class ConversationMessageShown {
    private final int position;

    public ConversationMessageShown(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
