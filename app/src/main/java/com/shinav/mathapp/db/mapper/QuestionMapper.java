package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.question.Question;
import com.squareup.sqlbrite.SqlBrite;

public class QuestionMapper implements rx.functions.Func1<SqlBrite.Query, Question> {

    @Override public Question call(SqlBrite.Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return Question.fromCursor(c);
        } finally {
            c.close();
        }
    }

}
