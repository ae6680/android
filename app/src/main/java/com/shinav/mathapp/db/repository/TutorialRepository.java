package com.shinav.mathapp.db.repository;

import android.database.Cursor;

import com.shinav.mathapp.db.cursorParser.TutorialCursorParser;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;

public class TutorialRepository {

    @Inject SqlBrite db;
    @Inject TutorialCursorParser parser;

    @Inject
    public TutorialRepository() {
    }

    public Tutorial getByPerspective(String perspective) {
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
