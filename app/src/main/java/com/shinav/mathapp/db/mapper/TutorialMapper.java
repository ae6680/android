package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class TutorialMapper implements rx.functions.Func1<Query, Tutorial> {

    @Inject SqlBrite db;

    @Inject
    public TutorialMapper() { }

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

    private Tutorial fromCursor(Cursor c) {
        Tutorial tutorial = new Tutorial();

        tutorial.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorial.setPerspective(c.getString(c.getColumnIndex(PERSPECTIVE)));

        return tutorial;
    }

    private ContentValues getContentValues(Tutorial tutorial) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorial.getKey());
        values.put(PERSPECTIVE, tutorial.getPerspective());

        return values;
    }

    public void insert(Tutorial tutorial) {
        db.insert(TABLE_NAME, getContentValues(tutorial));
    }

    public Tutorial getByPerspective(String perspective) {
        Cursor c = db.query(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PERSPECTIVE + " = ?"
                , perspective
        );

        try {
            return c.moveToFirst() ? fromCursor(c) : null;
        } finally {
            c.close();
        }
    }
    
}
