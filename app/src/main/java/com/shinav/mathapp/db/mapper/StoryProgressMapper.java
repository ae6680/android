package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.StoryProgress;
import com.squareup.sqlbrite.SqlBrite;

public class StoryProgressMapper implements rx.functions.Func1<SqlBrite.Query, StoryProgress> {

    @Override public StoryProgress call(SqlBrite.Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return StoryProgress.fromCursor(c);
        } finally {
            c.close();
        }
    }
}
