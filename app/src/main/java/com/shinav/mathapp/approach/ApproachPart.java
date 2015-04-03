package com.shinav.mathapp.approach;

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

        approachPart.setKey(getString(c, KEY));
        approachPart.setApproachKey(getString(c, APPROACH_KEY));
        approachPart.setPosition(getInt(c, POSITION));
        approachPart.setValue(getString(c, VALUE));

        return approachPart;
    }

    private static String getString(Cursor c, String column) {
        return c.getString(c.getColumnIndex(column));
    }

    private static int getInt(Cursor c, String column) {
        return c.getInt(c.getColumnIndex(column));
    }

}
