package com.shinav.mathapp.db.repository;

import android.database.Cursor;

import com.shinav.mathapp.db.cursorParser.TutorialPartCursorParser;
import com.shinav.mathapp.db.pojo.TutorialPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TUTORIAL_KEY;

public class TutorialPartRepository {

    @Inject SqlBrite db;
    @Inject TutorialPartCursorParser parser;

    @Inject
    public TutorialPartRepository() {
    }

    public List<TutorialPart> getByTutorialKey(String tutorialKey) {
        Cursor c = db.query(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + TUTORIAL_KEY + " = ?"
                , tutorialKey
        );

        try {

            List<TutorialPart> tutorialParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                tutorialParts.add(parser.fromCursor(c));
            }
            return tutorialParts;

        } finally {
            c.close();
        }

    }

}
