package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.QuestionExplanation;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TEXT;

public class QuestionExplanationDataMapper extends DataMapper<QuestionExplanation> {

    @Inject
    public QuestionExplanationDataMapper() { }

    @Override public ContentValues getContentValues(QuestionExplanation questionExplanation) {
        ContentValues values = new ContentValues();

        values.put(KEY, questionExplanation.getKey());
        values.put(QUESTION_KEY, questionExplanation.getQuestionKey());
        values.put(POSITION, questionExplanation.getPosition());
        values.put(TEXT, questionExplanation.getText());
        values.put(IMAGE_URL, questionExplanation.getImageUrl());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
