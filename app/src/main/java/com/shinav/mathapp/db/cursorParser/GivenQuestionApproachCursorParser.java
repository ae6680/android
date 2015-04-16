package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.GivenQuestionApproach;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.ARRANGEMENT;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.QUESTION_APPROACH_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class GivenQuestionApproachCursorParser implements Func1<Query, GivenQuestionApproach> {

    @Inject
    public GivenQuestionApproachCursorParser() { }

    @Override public GivenQuestionApproach call(Query query) {
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

    private GivenQuestionApproach fromCursor(Cursor c) {
        GivenQuestionApproach givenQuestionApproach = new GivenQuestionApproach();

        givenQuestionApproach.setKey(c.getString(c.getColumnIndex(KEY)));
        givenQuestionApproach.setApproachKey(c.getString(c.getColumnIndex(QUESTION_APPROACH_KEY)));
        givenQuestionApproach.setArrangement(c.getString(c.getColumnIndex(ARRANGEMENT)));
        givenQuestionApproach.setGivenAt(c.getInt(c.getColumnIndex(GIVEN_AT)));

        return givenQuestionApproach;
    }

}
