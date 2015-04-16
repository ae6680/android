package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Storyboard;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryboardCursorParser implements Func1<Query, Storyboard> {

    @Inject
    public StoryboardCursorParser() { }

    @Override public Storyboard call(Query query) {
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

    public Storyboard fromCursor(Cursor c) {
        Storyboard storyboard = new Storyboard();

        storyboard.setKey(c.getString(c.getColumnIndex(KEY)));
        storyboard.setTitle((c.getString(c.getColumnIndex(TITLE))));

        return storyboard;
    }

}
