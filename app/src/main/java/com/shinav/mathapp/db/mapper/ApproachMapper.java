package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.Approach;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class ApproachMapper implements rx.functions.Func1<Query, Approach> {
    @Override public Approach call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return Approach.fromCursor(c);
        } finally {
            c.close();
        }
    }
}
