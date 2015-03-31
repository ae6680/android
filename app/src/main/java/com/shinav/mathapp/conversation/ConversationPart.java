package com.shinav.mathapp.conversation;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class ConversationPart extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private String message;
    private int position;
    private int delay;
    private int typingDuration;

    public ConversationPart() { }

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

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public boolean isLeft() {
       return getPosition() == 0;
    }

}
