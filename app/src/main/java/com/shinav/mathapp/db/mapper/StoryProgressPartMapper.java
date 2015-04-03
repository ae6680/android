package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.main.storyProgress.StoryProgressPart;
import com.squareup.sqlbrite.SqlBrite;

public class StoryProgressPartMapper implements rx.functions.Func1<SqlBrite.Query, StoryProgressPart> {

    @Override public StoryProgressPart call(SqlBrite.Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return StoryProgressPart.fromCursor(c);
        } finally {
            c.close();
        }
    }

}
