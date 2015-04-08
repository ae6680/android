package com.shinav.mathapp.db.repository;

import android.database.Cursor;

import com.shinav.mathapp.db.cursorParser.StoryCursorParser;
import com.shinav.mathapp.db.pojo.Story;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Story.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Story.TABLE_NAME;

public class StoryRepository {

    @Inject SqlBrite db;
    @Inject StoryCursorParser parser;

    @Inject
    public StoryRepository() { }

    public Story getByPerspective(String perspective) {
        Cursor c = db.query(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PERSPECTIVE + " = ?"
                , perspective
        );

        try {
            return c.moveToFirst() ? parser.fromCursor(c) : null;
        } finally {
            c.close();
        }
    }
}
