package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.Approach;

import static com.shinav.mathapp.db.helper.Tables.Approach.KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.QUESTION_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ApproachMapper implements rx.functions.Func1<Query, Approach> {

    @Override public Approach call(Query query) {
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

    public Approach fromCursor(Cursor c) {
        Approach approach = new Approach();

        approach.setKey(c.getString(c.getColumnIndex(KEY)));
        approach.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));

        return approach;
    }
}
