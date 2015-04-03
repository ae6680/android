package com.shinav.mathapp.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.shinav.mathapp.db.helper.Tables.Question.VALUE;

public class Question {

    private String key;

    private String value;
    private String title;
    private String answer;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(KEY, getKey());
        values.put(VALUE, getValue());
        values.put(TITLE, getTitle());
        values.put(ANSWER, getAnswer());

        return values;
    }

    public static Question fromCursor(Cursor c) {
        Question question = new Question();

        question.setKey(c.getString(c.getColumnIndex(KEY)));
        question.setValue(c.getString(c.getColumnIndex(VALUE)));
        question.setTitle(c.getString(c.getColumnIndex(TITLE)));
        question.setAnswer(c.getString(c.getColumnIndex(ANSWER)));

        return question;
    }

}
