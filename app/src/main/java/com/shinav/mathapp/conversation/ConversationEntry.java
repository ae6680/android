package com.shinav.mathapp.conversation;

public class ConversationEntry {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private final int typingDuration;
    private final int delay;

    private String message;
    private int position;

    public ConversationEntry(String message, int position, int delay, int typingDuration) {
        this.message = message;
        this.position = position;
        this.typingDuration = typingDuration;
        this.delay = delay;
    }

    public String getMessage() {
        return message;
    }

    public int getPosition() {
        return position;
    }

    public int getTypingDuration() {
        return typingDuration;
    }

    public int getDelay() {
        return delay;
    }

}
