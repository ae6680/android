package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;

public class QuestionApproachPart implements Comparable<QuestionApproachPart> {

    private String key;
    private String approachKey;

    private int position;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getApproachKey() {
        return approachKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setApproachKey(String approachKey) {
        this.approachKey = approachKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override public int compareTo(@NonNull QuestionApproachPart another) {
        return getPosition() - another.getPosition();
    }

}
