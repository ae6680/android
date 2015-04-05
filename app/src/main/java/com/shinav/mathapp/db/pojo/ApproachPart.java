package com.shinav.mathapp.db.pojo;

public class ApproachPart {

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

}
