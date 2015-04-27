package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public class StoryboardFrame implements Comparable<StoryboardFrame> {

    private String key;
    private String storyboardKey;

    private int position;
    private String frameType;
    private String frameTypeKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStoryboardKey() {
        return storyboardKey;
    }

    public void setStoryboardKey(String storyboardKey) {
        this.storyboardKey = storyboardKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public String getFrameTypeKey() {
        return frameTypeKey;
    }

    public void setFrameTypeKey(String frameTypeKey) {
        this.frameTypeKey = frameTypeKey;
    }

    public boolean isCutscene() {
        return TextUtils.equals(getFrameType(), "cutscene");
    }

    public boolean isQuestion() {
        return TextUtils.equals(getFrameType(), "question");
    }

    @Override public int compareTo(@NonNull StoryboardFrame another) {
        return getPosition() - another.getPosition();
    }
}
