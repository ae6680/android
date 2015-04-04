package com.shinav.mathapp.db.model;

import android.content.ContentValues;

import com.shinav.mathapp.db.helper.Tables;

import java.util.List;

public class Approach {

    private String key;
    private String questionKey;
    private List<ApproachPart> approachParts;

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

    public void setApproachParts(List<ApproachPart> approachParts) {
        this.approachParts = approachParts;
    }

    public List<ApproachPart> getApproachParts() {
        return approachParts;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(Tables.Approach.KEY, getKey());
        values.put(Tables.Approach.QUESTION_KEY, getQuestionKey());

        return values;
    }
}
