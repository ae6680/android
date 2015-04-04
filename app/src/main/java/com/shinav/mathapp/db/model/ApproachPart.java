package com.shinav.mathapp.db.model;

import android.content.ContentValues;

import static com.shinav.mathapp.db.helper.Tables.ApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.VALUE;

public class ApproachPart {

    private String key;
    private String approachKey;

    private int position;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getApproachKey() {
        return approachKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setApproachKey(String approachKey) {
        this.approachKey = approachKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(KEY, getKey());
        values.put(APPROACH_KEY, getApproachKey());
        values.put(VALUE, getValue());
        values.put(POSITION, getPosition());

        return values;
    }
}
