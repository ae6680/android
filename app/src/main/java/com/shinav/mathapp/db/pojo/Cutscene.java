package com.shinav.mathapp.db.pojo;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;

public class Cutscene {

    private String key;
    private String title;
    private String backgroundImageUrl;
    private int state = STATE_CLOSED;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
