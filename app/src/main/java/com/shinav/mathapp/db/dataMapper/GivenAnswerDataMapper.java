package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.GivenAnswer;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.VALUE;

public class GivenAnswerDataMapper extends DataMapper<GivenAnswer> {

    @Inject
    public GivenAnswerDataMapper() { }

    @Override public ContentValues getContentValues(GivenAnswer givenAnswer) {
        ContentValues values = new ContentValues();

        values.put(KEY, UUID.randomUUID().toString());
        values.put(QUESTION_KEY, givenAnswer.getQuestionKey());
        values.put(VALUE, givenAnswer.getValue());
        values.put(GIVEN_AT, new Date().getTime());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
