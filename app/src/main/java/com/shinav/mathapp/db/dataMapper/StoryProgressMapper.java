package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.StoryProgress;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgress.TABLE_NAME;

public class StoryProgressMapper {

    @Inject SqlBrite db;

    @Inject
    public StoryProgressMapper() { }

    private ContentValues getContentValues(StoryProgress storyProgress) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyProgress.getKey());

        return values;
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
