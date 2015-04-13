package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryProgress;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryProgressCursorParser implements Func1<Query, StoryProgress>{

    @Inject
    public StoryProgressCursorParser() { }

    @Override public StoryProgress call(Query query) {
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
