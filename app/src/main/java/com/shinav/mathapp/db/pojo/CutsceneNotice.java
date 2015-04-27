package com.shinav.mathapp.db.pojo;

import android.support.annotation.NonNull;

public class CutsceneNotice implements Comparable<CutsceneNotice> {

    public static final int ALIGNMENT_ABOVE = 0;
    public static final int ALIGNMENT_UNDER = 1;

    private String key;

    private String cutsceneKey;

    private String text;
    private String imageUrl;
    private int alignment;
    private int position;

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

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public boolean isAlignedAbove() {
        return getAlignment() == ALIGNMENT_ABOVE;
    }

    public boolean isAlignedUnder() {
        return getAlignment() == ALIGNMENT_UNDER;
    }

    @Override public int compareTo(@NonNull CutsceneNotice another) {
        return getPosition() - another.getPosition();
    }

}
