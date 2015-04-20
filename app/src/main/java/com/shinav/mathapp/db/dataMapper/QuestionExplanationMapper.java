package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.QuestionExplanation;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TEXT;

public class QuestionExplanationMapper {

    @Inject SqlBrite db;

    @Inject
    public QuestionExplanationMapper() { }

    private ContentValues getContentValues(QuestionExplanation questionExplanation) {
        ContentValues values = new ContentValues();

        values.put(KEY, questionExplanation.getKey());
        values.put(QUESTION_KEY, questionExplanation.getQuestionKey());
        values.put(POSITION, questionExplanation.getPosition());
        values.put(TEXT, questionExplanation.getText());
        values.put(IMAGE_URL, questionExplanation.getImageUrl());

        return values;
    }

    public void insert(com.shinav.mathapp.db.pojo.QuestionExplanation questionExplanation) {
        db.insert(TABLE_NAME, getContentValues(questionExplanation));
    }

    public void update(QuestionExplanation questionExplanation) {
        db.update(
                TABLE_NAME,
                getContentValues(questionExplanation),
                KEY + " = ?",
                questionExplanation.getKey()
        );
    }

    public void delete(String questionExplanationKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                questionExplanationKey
        );
    }

}
