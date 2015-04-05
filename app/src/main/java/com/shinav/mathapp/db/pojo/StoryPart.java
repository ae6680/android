package com.shinav.mathapp.db.pojo;

import android.text.TextUtils;

public class StoryPart {

    private String key;
    private String storyKey;

    private int position;
    private String type;
    private String typeKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStoryKey() {
        return storyKey;
    }

    public void setStoryKey(String storyKey) {
        this.storyKey = storyKey;
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

    public boolean isConversation() {
        return getType().equals("conversation");
    }

    public boolean isQuestion() {
        return TextUtils.equals(getType(), "question");
    }

}
