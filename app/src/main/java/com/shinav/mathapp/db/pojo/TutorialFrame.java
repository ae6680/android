package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;

public class TutorialFrame implements Comparable<TutorialFrame> {

    public static final String CUTSCENE = "cutscene";
    public static final String APPROACH = "approach";
    public static final String APPROACH_FEEDBACK = "approach_feedback";
    public static final String QUESTION = "question";

    private String key;

    private int position;
    private String tutorialKey;

    private String frameType;
    private String frameTypeKey;

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

    @Override public int compareTo(@NonNull TutorialFrame another) {
        return getPosition() - another.getPosition();
    }
}
