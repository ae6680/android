package com.shinav.mathapp.db.pojo;

public class ConversationPart {

    private String key;

    private String conversationKey;

    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

}
