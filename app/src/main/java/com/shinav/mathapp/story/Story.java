package com.shinav.mathapp.story;

import android.content.ContentValues;

import com.shinav.mathapp.db.helper.Tables;

public class Story {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(Tables.Story.KEY, getKey());

        return values;
    }
}
