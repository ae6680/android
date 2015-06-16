package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryboardFrame;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.STORYBOARD_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryboardFrameListMapper extends ListMapper<StoryboardFrame> {

    @Inject
    public StoryboardFrameListMapper() { }

    @Override public List<StoryboardFrame> call(Query query) {
        List<StoryboardFrame> storyboardFrames = super.call(query);
        Collections.sort(storyboardFrames);

        return storyboardFrames;
    }

    @Override public StoryboardFrame fromCursor(Cursor c) {
        StoryboardFrame storyboardFrame = new StoryboardFrame();

        storyboardFrame.setKey(c.getString(c.getColumnIndex(KEY)));
        storyboardFrame.setStoryboardKey(c.getString(c.getColumnIndex(STORYBOARD_KEY)));
        storyboardFrame.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        storyboardFrame.setFrameType(c.getString(c.getColumnIndex(FRAME_TYPE)));
        storyboardFrame.setFrameTypeKey(c.getString(c.getColumnIndex(FRAME_TYPE_KEY)));

        return storyboardFrame;
    }

}
