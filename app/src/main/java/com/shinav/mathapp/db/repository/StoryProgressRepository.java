package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryProgressCursorParser;
import com.shinav.mathapp.db.pojo.StoryProgress;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.StoryProgress.TABLE_NAME;

public class StoryProgressRepository {

    @Inject SqlBrite db;
    @Inject StoryProgressCursorParser parser;

    @Inject
    public StoryProgressRepository() { }

    public Observable<StoryProgress> getFirst() {

        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME
        ).map(parser);
    }

}
