package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.GivenAnswer;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class GivenAnswerCursorParser implements Func1<Query, GivenAnswer> {

    @Override public GivenAnswer call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToLast()) {
                return null;
            }

            return fromCursor(c);
        } finally {
            c.close();
        }
    }

    private GivenAnswer fromCursor(Cursor c) {
        GivenAnswer givenAnswer = new GivenAnswer();

        givenAnswer.setKey(c.getString(c.getColumnIndex(KEY)));
        givenAnswer.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));
        givenAnswer.setValue(c.getString(c.getColumnIndex(VALUE)));
        givenAnswer.setGivenAt(c.getInt(c.getColumnIndex(GIVEN_AT)));

        return givenAnswer;
    }

}
