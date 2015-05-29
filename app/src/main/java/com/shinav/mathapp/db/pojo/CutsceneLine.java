package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;

public class CutsceneLine implements Comparable<CutsceneLine> {

    public static final int ALIGNMENT_RIGHT = 1;

    private String key;

    private String cutsceneKey;

    private String value;
    private int position;
    private int delay;
    private int typingDuration;
    private int alignment;
    private String imageUrl;
    private int mainCharacter;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCutsceneKey() {
        return cutsceneKey;
    }

    public void setCutsceneKey(String cutsceneKey) {
        this.cutsceneKey = cutsceneKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getTypingDuration() {
        return typingDuration;
    }

    public void setTypingDuration(int typingDuration) {
        this.typingDuration = typingDuration;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(int mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public boolean isMainCharacter() {
        return mainCharacter == 1;
    }

    @Override public int compareTo(@NonNull CutsceneLine another) {
        return getPosition() - another.getPosition();
    }

}
