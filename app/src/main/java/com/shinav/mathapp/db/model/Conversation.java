package com.shinav.mathapp.db.model;

import android.content.ContentValues;

import com.shinav.mathapp.db.helper.Tables;

public class Conversation {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(Tables.Conversation.KEY, getKey());

        return values;
    }
}
