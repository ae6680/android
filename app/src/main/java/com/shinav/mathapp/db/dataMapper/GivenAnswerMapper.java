package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.GivenAnswer;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.VALUE;

public class GivenAnswerMapper {

    @Inject SqlBrite db;

    @Inject
    public GivenAnswerMapper() { }

    private ContentValues getContentValues(GivenAnswer givenAnswer) {
        ContentValues values = new ContentValues();

        values.put(KEY, UUID.randomUUID().toString());
        values.put(QUESTION_KEY, givenAnswer.getQuestionKey());
        values.put(VALUE, givenAnswer.getValue());
        values.put(GIVEN_AT, new Date().getTime());

        return values;
    }

    public void insert(GivenAnswer givenAnswer) {
        db.insert(TABLE_NAME, getContentValues(givenAnswer));
    }

    public void delete(String questionKey) {
        db.delete(
                TABLE_NAME,
                QUESTION_KEY + " = ?",
                questionKey
        );
    }

}
