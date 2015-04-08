package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.StoryPart;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.STORY_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE_KEY;

public class StoryPartMapper {

    @Inject SqlBrite db;

    @Inject
    public StoryPartMapper() { }

    private ContentValues getContentValues(StoryPart storyPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyPart.getKey());
        values.put(STORY_KEY, storyPart.getStoryKey());
        values.put(TYPE, storyPart.getType());
        values.put(TYPE_KEY, storyPart.getTypeKey());
        values.put(POSITION, storyPart.getPosition());

        return values;
    }

    public void insert(StoryPart storyPart) {
        db.insert(TABLE_NAME, getContentValues(storyPart));
    }

}
