package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryProgressPartCursorParser;
import com.shinav.mathapp.db.cursorParser.StoryProgressPartListCursorParser;
import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.TABLE_NAME;

public class StoryProgressPartRepository {

    @Inject SqlBrite db;
    @Inject StoryProgressPartCursorParser singleParser;
    @Inject StoryProgressPartListCursorParser listParser;

    @Inject
    public StoryProgressPartRepository() { }

    public Observable<StoryProgressPart> getByQuestionKey(String questionKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        )
                .map(singleParser);
    }

    public Observable<List<StoryProgressPart>> getByStoryProgressKey(String storyProgressKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORY_PROGRESS_KEY + " = ?"
                , storyProgressKey
        )
                .map(listParser);
    }


}
