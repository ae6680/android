package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryProgress;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgress.TABLE_NAME;

public class StoryProgressMapper {

    private final SqlBrite db;

    @Inject
    public StoryProgressMapper(SqlBrite db) {
        this.db = db;
    }

    public StoryProgress fromCursor(Cursor c) {
        StoryProgress storyProgress = new StoryProgress();

        storyProgress.setKey(c.getString(c.getColumnIndex(KEY)));

        return storyProgress;
    }

    public ContentValues getContentValues(StoryProgress storyProgress) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyProgress.getKey());

        return values;
    }

    public List<StoryProgress> getAll() {
        List<StoryProgress> list = new ArrayList<>();

        Cursor c = db.query("SELECT * FROM " + TABLE_NAME);
        while (c.moveToNext()) {
            list.add(fromCursor(c));
        }
        c.close();

        return list;
    }

    public void insert(StoryProgress storyProgress) {
        db.insert(TABLE_NAME, getContentValues(storyProgress));
    }

    public void update(StoryProgress storyProgress) {
        db.update(
                TABLE_NAME,
                getContentValues(storyProgress),
                KEY + " = ?",
                storyProgress.getKey()
        );
    }
}
