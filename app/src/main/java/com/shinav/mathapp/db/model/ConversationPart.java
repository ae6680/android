package com.shinav.mathapp.db.model;

import android.database.Cursor;

import static com.shinav.mathapp.db.helper.Tables.ConversationPart.DELAY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.MESSAGE;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TYPING_DURATION;

public class ConversationPart {

    private String key;

    private String message;
    private int position;
    private int delay;
    private int typingDuration;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public boolean isLeft() {
       return getPosition() == 0;
    }

    public static ConversationPart fromCursor(Cursor c) {
        ConversationPart conversationPart = new ConversationPart();

        conversationPart.setKey(c.getString(c.getColumnIndex(KEY)));
        conversationPart.setMessage(c.getString(c.getColumnIndex(MESSAGE)));
        conversationPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        conversationPart.setDelay(c.getInt(c.getColumnIndex(DELAY)));
        conversationPart.setTypingDuration(c.getInt(c.getColumnIndex(TYPING_DURATION)));

        return conversationPart;
    }

}
