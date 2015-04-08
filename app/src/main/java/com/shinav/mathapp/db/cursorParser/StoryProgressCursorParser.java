package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryProgress;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.KEY;

public class StoryProgressCursorParser {

    @Inject
    public StoryProgressCursorParser() { }

    public StoryProgress fromCursor(Cursor c) {
        StoryProgress storyProgress = new StoryProgress();

        storyProgress.setKey(c.getString(c.getColumnIndex(KEY)));

        return storyProgress;
    }

}
