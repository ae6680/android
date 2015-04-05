package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Story;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Story.KEY;
import static com.shinav.mathapp.db.helper.Tables.Story.TABLE_NAME;

public class StoryMapper {

    private final SqlBrite db;

    @Inject
    public StoryMapper(SqlBrite db) {
        this.db = db;
    }

    public ContentValues getContentValues(Story story) {
        ContentValues values = new ContentValues();

        values.put(KEY, story.getKey());

        return values;
    }

    public void insert(Story story) {
        db.insert(TABLE_NAME, getContentValues(story));
    }

}
