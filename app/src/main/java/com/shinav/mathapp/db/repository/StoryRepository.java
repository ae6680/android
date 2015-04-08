package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryCursorParser;
import com.shinav.mathapp.db.pojo.Story;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Story.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Story.TABLE_NAME;

public class StoryRepository {

    @Inject SqlBrite db;
    @Inject StoryCursorParser parser;

    @Inject
    public StoryRepository() { }

    public Observable<Story> getByPerspective(String perspective) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PERSPECTIVE + " = ?"
                , perspective
        ).map(parser);
    }

}
