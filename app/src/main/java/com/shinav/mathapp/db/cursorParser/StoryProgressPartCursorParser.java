package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryProgressPart;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STATE;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryProgressPartCursorParser implements Func1<Query, StoryProgressPart> {

    @Inject
    public StoryProgressPartCursorParser() { }

    @Override public StoryProgressPart call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return fromCursor(c);
        } finally {
            c.close();
        }
    }

    public StoryProgressPart fromCursor(Cursor c) {
        StoryProgressPart storyProgressPart = new StoryProgressPart();

        storyProgressPart.setKey(c.getString(c.getColumnIndex(KEY)));
        storyProgressPart.setStoryProgressKey(c.getString(c.getColumnIndex(STORY_PROGRESS_KEY)));
        storyProgressPart.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));
        storyProgressPart.setTitle(c.getString(c.getColumnIndex(TITLE)));
        storyProgressPart.setState(c.getInt(c.getColumnIndex(STATE)));

        return storyProgressPart;
    }

}
