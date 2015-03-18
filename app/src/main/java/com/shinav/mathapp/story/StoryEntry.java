package com.shinav.mathapp.story;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class StoryEntry extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private int position;
    private String type;
    private String typeKey;

    public StoryEntry() {  }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public boolean isApproach() {
        return getType().equals("approach");
    }

    public boolean isApproachFeedback() {
        return getType().equals("approachFeedback");
    }

    public boolean isConversation() {
        return getType().equals("conversation");
    }

    public boolean isQuestion() {
        return getType().equals("question");
    }

}
