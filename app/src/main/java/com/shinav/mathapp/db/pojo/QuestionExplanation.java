package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;

public class QuestionExplanation implements Comparable<QuestionExplanation> {

    private String key;
    private String questionKey;
    private String text;
    private String imageUrl;
    private int position;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override public int compareTo(@NonNull QuestionExplanation another) {
        return (getPosition() - another.getPosition());
    }

}
