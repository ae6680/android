package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.StoryProgress;
import com.squareup.sqlbrite.SqlBrite;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;

public class StoryProgressMapper implements rx.functions.Func1<SqlBrite.Query, StoryProgress> {

    @Override public StoryProgress call(SqlBrite.Query query) {
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

    public StoryProgress fromCursor(Cursor c) {
        StoryProgress storyProgress = new StoryProgress();

        storyProgress.setKey(c.getString(c.getColumnIndex(KEY)));

        return storyProgress;
    }

}
