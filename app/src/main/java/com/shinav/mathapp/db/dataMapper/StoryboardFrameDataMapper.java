package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.StoryboardFrame;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.STORYBOARD_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.TABLE_NAME;

public class StoryboardFrameDataMapper extends DataMapper<StoryboardFrame> {

    @Inject
    public StoryboardFrameDataMapper() { }

    @Override public ContentValues getContentValues(StoryboardFrame storyboardFrame) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyboardFrame.getKey());
        values.put(STORYBOARD_KEY, storyboardFrame.getStoryboardKey());
        values.put(FRAME_TYPE, storyboardFrame.getFrameType());
        values.put(FRAME_TYPE_KEY, storyboardFrame.getFrameTypeKey());
        values.put(POSITION, storyboardFrame.getPosition());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
