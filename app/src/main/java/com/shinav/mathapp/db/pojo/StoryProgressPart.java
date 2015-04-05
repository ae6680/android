package com.shinav.mathapp.db.pojo;

public class StoryProgressPart {

    public static final int STATE_UNMADE = 0;
    public static final int STATE_PASS = 1;
    public static final int STATE_FAIL = 2;

    private String key;

    private String storyProgressKey;
    private String questionKey;
    private String title;
    private int state;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStoryProgressKey() {
        return storyProgressKey;
    }

    public void setStoryProgressKey(String storyProgressKey) {
        this.storyProgressKey = storyProgressKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}

