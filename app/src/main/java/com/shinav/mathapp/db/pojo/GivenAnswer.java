package com.shinav.mathapp.db.pojo;

public class GivenAnswer {

    private String key;
    private String questionKey;
    private String value;
    private int givenAt;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getGivenAt() {
        return givenAt;
    }

    public void setGivenAt(int givenAt) {
        this.givenAt = givenAt;
    }

}
