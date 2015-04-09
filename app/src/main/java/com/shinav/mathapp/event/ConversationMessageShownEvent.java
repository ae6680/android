package com.shinav.mathapp.event;

public class ConversationMessageShownEvent {
    private final int position;

    public ConversationMessageShownEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
