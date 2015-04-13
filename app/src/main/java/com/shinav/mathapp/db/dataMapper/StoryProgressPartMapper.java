package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STATE;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.TITLE;

public class StoryProgressPartMapper {

    @Inject SqlBrite db;

    @Inject
    public StoryProgressPartMapper() { }

    private ContentValues getContentValues(StoryProgressPart part) {
        ContentValues values = new ContentValues();

        values.put(KEY, part.getKey());
        values.put(STORY_PROGRESS_KEY, part.getStoryProgressKey());
        values.put(QUESTION_KEY, part.getQuestionKey());
        values.put(TITLE, part.getTitle());
        values.put(STATE, part.getState());

        return values;
    }

    public void insert(StoryProgressPart part) {
        db.insert(TABLE_NAME, getContentValues(part));
    }

    public void update(StoryProgressPart part) {
        db.update(
                TABLE_NAME,
                getContentValues(part),
                KEY + " = ?",
                part.getKey()
        );
    }

}
