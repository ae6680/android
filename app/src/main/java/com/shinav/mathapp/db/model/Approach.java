package com.shinav.mathapp.db.model;

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

        approach.setKey(c.getString(c.getColumnIndex(KEY)));
        approach.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));

        return approach;
    }

}
