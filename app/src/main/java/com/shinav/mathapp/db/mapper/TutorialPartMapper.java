package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.TutorialPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TUTORIAL_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TYPE_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class TutorialPartMapper implements rx.functions.Func1<Query, List<TutorialPart>> {

    @Inject SqlBrite db;

    @Inject
    public TutorialPartMapper() {
    }

    @Override public List<TutorialPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<TutorialPart> tutorialParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                tutorialParts.add(fromCursor(c));
            }
            return tutorialParts;
        } finally {
            c.close();
        }
    }

    private TutorialPart fromCursor(Cursor c) {
        TutorialPart tutorialPart = new TutorialPart();

        tutorialPart.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorialPart.setTutorialKey(c.getString(c.getColumnIndex(TUTORIAL_KEY)));
        tutorialPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        tutorialPart.setType(c.getString(c.getColumnIndex(TYPE)));
        tutorialPart.setTypeKey(c.getString(c.getColumnIndex(TYPE_KEY)));

        return tutorialPart;
    }

    public void insert(TutorialPart tutorialPart) {
        db.insert(TABLE_NAME, getContentValues(tutorialPart));
    }

    private ContentValues getContentValues(TutorialPart tutorialPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorialPart.getKey());
        values.put(TUTORIAL_KEY, tutorialPart.getTutorialKey());
        values.put(TYPE, tutorialPart.getType());
        values.put(TYPE_KEY, tutorialPart.getTypeKey());
        values.put(POSITION, tutorialPart.getPosition());

        return values;
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
                tutorialParts.add(fromCursor(c));
            }
            return tutorialParts;

        } finally {
            c.close();
        }
    }

}
