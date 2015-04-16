package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.QuestionApproach;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.QUESTION_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionApproachCursorParser implements Func1<Query, QuestionApproach> {

    @Inject
    public QuestionApproachCursorParser() { }

    @Override public QuestionApproach call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return fromCursor(c);
        } finally {
            c.close();
        }
    }

    public QuestionApproach fromCursor(Cursor c) {
        QuestionApproach questionApproach = new QuestionApproach();

        questionApproach.setKey(c.getString(c.getColumnIndex(KEY)));
        questionApproach.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));

        return questionApproach;
    }

}
