package com.shinav.mathapp.approach;

import android.database.Cursor;

import static com.shinav.mathapp.db.helper.Tables.Approach.KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.QUESTION_KEY;

public class Approach {

    private String key;
    private String questionKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public static Approach fromCursor(Cursor c) {
        Approach approach = new Approach();

        approach.setKey(getString(c, KEY));
        approach.setQuestionKey(getString(c, QUESTION_KEY));

        return approach;
    }

    private static String getString(Cursor c, String column) {
        return c.getString(c.getColumnIndex(column));
    }

}
