package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;

public class ConversationLine implements Comparable<ConversationLine> {

    public static final int ALIGNMENT_LEFT = 0;
    public static final int ALIGNMENT_RIGHT = 1;

    private String key;

    private String conversationKey;

    private String value;
    private int position;
    private int delay;
    private int typingDuration;
    private int alignment;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getConversationKey() {
        return conversationKey;
    }

    public void setConversationKey(String conversationKey) {
        this.conversationKey = conversationKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getTypingDuration() {
        return typingDuration;
    }

    public void setTypingDuration(int typingDuration) {
        this.typingDuration = typingDuration;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    @Override public int compareTo(@NonNull ConversationLine another) {
        return (getPosition() - another.getPosition());
    }

}
