package com.shinav.mathapp.db.pojo;

public class Question {

    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPENED = 1;
    public static final int STATE_PASSED = 2;
    public static final int STATE_FAILED = 3;

    private String key;

    private String value;
    private String title;
    private String answer;
    private String annexImageUrl;
    private String backgroundImageUrl;
    private int progressState;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnnexImageUrl() {
        return annexImageUrl;
    }

    public void setAnnexImageUrl(String annexImageUrl) {
        this.annexImageUrl = annexImageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public int getProgressState() {
        return progressState;
    }

    public void setProgressState(int progressState) {
        this.progressState = progressState;
    }
}
