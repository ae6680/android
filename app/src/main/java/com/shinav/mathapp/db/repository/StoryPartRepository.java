package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryPartCursorParser;
import com.shinav.mathapp.db.pojo.StoryPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.StoryPart.STORY_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TABLE_NAME;

public class StoryPartRepository {

    @Inject SqlBrite db;

    @Inject
    public StoryPartRepository() {
    }

    public Observable<List<StoryPart>> getByStoryKey(String storyKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORY_KEY + " = ?"
                , storyKey
        ).map(new StoryPartCursorParser());
    }

}
