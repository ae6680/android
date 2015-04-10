package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Question;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.EXPLANATION;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.shinav.mathapp.db.helper.Tables.Question.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionCursorParser implements rx.functions.Func1<Query, Question> {

    @Inject
    public QuestionCursorParser() { }

    @Override public Question call(Query query) {
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

    public Question fromCursor(Cursor c) {
        Question question = new Question();

        question.setKey(c.getString(c.getColumnIndex(KEY)));
        question.setValue(c.getString(c.getColumnIndex(VALUE)));
        question.setTitle(c.getString(c.getColumnIndex(TITLE)));
        question.setAnswer(c.getString(c.getColumnIndex(ANSWER)));
        question.setExplanation(c.getString(c.getColumnIndex(EXPLANATION)));

        return question;
    }

}
