package com.shinav.mathapp.db.pojo;

import android.text.TextUtils;

public class TutorialPart {

    private String key;

    private int position;
    private String tutorialKey;

    private String type;
    private String typeKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTutorialKey() {
        return tutorialKey;
    }

    public void setTutorialKey(String tutorialKey) {
        this.tutorialKey = tutorialKey;
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

    public boolean isConversation() {
        return TextUtils.equals(getType(), "conversation");
    }

    public boolean isQuestion() {
        return TextUtils.equals(getType(), "question");
    }

}
