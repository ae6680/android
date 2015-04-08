package com.shinav.mathapp.db.repository;

import android.database.Cursor;

import com.shinav.mathapp.db.cursorParser.StoryProgressCursorParser;
import com.shinav.mathapp.db.pojo.StoryProgress;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.TABLE_NAME;

public class StoryProgressRepository {

    @Inject SqlBrite db;
    @Inject StoryProgressCursorParser parser;

    @Inject
    public StoryProgressRepository() { }

    public List<StoryProgress> getAll() {
        List<StoryProgress> list = new ArrayList<>();

        Cursor c = db.query("SELECT * FROM " + TABLE_NAME);
        while (c.moveToNext()) {
            list.add(parser.fromCursor(c));
        }
        c.close();

        return list;
    }

}
