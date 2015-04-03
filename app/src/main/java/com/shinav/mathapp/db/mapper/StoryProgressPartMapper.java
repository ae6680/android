package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.StoryProgressPart;
import com.squareup.sqlbrite.SqlBrite;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.GIVEN_ANSWER;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;

public class StoryProgressPartMapper implements rx.functions.Func1<SqlBrite.Query, StoryProgressPart> {

    @Override public StoryProgressPart call(SqlBrite.Query query) {
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
        storyProgressPart.setGivenAnswer(c.getString(c.getColumnIndex(GIVEN_ANSWER)));

        return storyProgressPart;
    }

}
