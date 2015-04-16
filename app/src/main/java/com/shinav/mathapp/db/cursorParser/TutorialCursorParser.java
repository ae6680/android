package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Tutorial;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class TutorialCursorParser implements Func1<Query, Tutorial> {

    @Inject
    public TutorialCursorParser() { }

    @Override public Tutorial call(Query query) {
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

    public Tutorial fromCursor(Cursor c) {
        Tutorial tutorial = new Tutorial();

        tutorial.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorial.setTitle(c.getString(c.getColumnIndex(TITLE)));

        return tutorial;
    }
}
