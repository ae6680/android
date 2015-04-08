package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Story;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.Story.KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryCursorParser implements Func1<Query, Story> {

    @Inject
    public StoryCursorParser() { }

    @Override public Story call(Query query) {
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

    public Story fromCursor(Cursor c) {
        Story story = new Story();

        story.setKey(c.getString(c.getColumnIndex(KEY)));

        return story;
    }

}
