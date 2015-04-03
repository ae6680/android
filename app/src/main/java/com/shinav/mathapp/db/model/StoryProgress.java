package com.shinav.mathapp.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;

public class StoryProgress {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static StoryProgress fromCursor(Cursor c) {
        StoryProgress storyProgress = new StoryProgress();

        storyProgress.setKey(c.getString(c.getColumnIndex(KEY)));

        return storyProgress;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(KEY, getKey());

        return values;
    }
}
