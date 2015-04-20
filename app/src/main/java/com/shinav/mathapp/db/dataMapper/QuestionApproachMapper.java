package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.TABLE_NAME;

public class QuestionApproachMapper {

    @Inject SqlBrite db;

    @Inject
    public QuestionApproachMapper() { }

    private ContentValues getContentValues(QuestionApproach questionApproach) {
        ContentValues values = new ContentValues();

        values.put(KEY, questionApproach.getKey());
        values.put(QUESTION_KEY, questionApproach.getQuestionKey());

        return values;
    }

    public void insert(QuestionApproach questionApproach) {
        db.insert(TABLE_NAME, getContentValues(questionApproach));
    }

    public void update(QuestionApproach questionApproach) {
        db.update(
                TABLE_NAME,
                getContentValues(questionApproach),
                KEY + " = ?",
                questionApproach.getKey()
        );
    }

    public void delete(String questionApproachKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                questionApproachKey
        );
    }

}
