package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryboardFrameCursorParser;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.STORYBOARD_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.TABLE_NAME;

public class StoryboardFrameRepository {

    @Inject SqlBrite db;
    @Inject StoryboardFrameCursorParser parser;

    @Inject
    public StoryboardFrameRepository() {
    }

    public Observable<List<StoryboardFrame>> getByStoryboardKey(String storyKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORYBOARD_KEY + " = ?"
                , storyKey
        ).map(parser);
    }

}
