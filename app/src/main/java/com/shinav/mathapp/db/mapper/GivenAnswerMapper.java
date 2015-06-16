package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.GivenAnswer;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.VALUE;

public class GivenAnswerMapper extends Mapper<GivenAnswer> {

    @Inject
    public GivenAnswerMapper() { }

    @Override public GivenAnswer fromCursor(Cursor c) {
        GivenAnswer givenAnswer = new GivenAnswer();

        givenAnswer.setKey(c.getString(c.getColumnIndex(KEY)));
        givenAnswer.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));
        givenAnswer.setValue(c.getString(c.getColumnIndex(VALUE)));
        givenAnswer.setGivenAt(c.getInt(c.getColumnIndex(GIVEN_AT)));

        return givenAnswer;
    }

}
