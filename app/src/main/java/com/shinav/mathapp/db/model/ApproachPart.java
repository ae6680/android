package com.shinav.mathapp.db.model;

import android.database.Cursor;

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

    public static ApproachPart fromCursor(Cursor c) {
        ApproachPart approachPart = new ApproachPart();

        approachPart.setKey(c.getString(c.getColumnIndex(KEY)));
        approachPart.setApproachKey(c.getString(c.getColumnIndex(APPROACH_KEY)));
        approachPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        approachPart.setValue(c.getString(c.getColumnIndex(VALUE)));

        return approachPart;
    }

}
