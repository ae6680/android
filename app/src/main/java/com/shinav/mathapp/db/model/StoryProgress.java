package com.shinav.mathapp.db.model;

import android.content.ContentValues;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;

public class StoryProgress {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(KEY, getKey());

        return values;
    }
}
