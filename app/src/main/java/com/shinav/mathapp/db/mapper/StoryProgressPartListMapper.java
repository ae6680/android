package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.StoryProgressPart;

import java.util.ArrayList;
import java.util.List;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.GIVEN_ANSWER;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryProgressPartListMapper implements rx.functions.Func1<Query, List<StoryProgressPart>> {

    @Override public List<StoryProgressPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<StoryProgressPart> storyProgressParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyProgressParts.add(fromCursor(c));
            }
            return storyProgressParts;
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
