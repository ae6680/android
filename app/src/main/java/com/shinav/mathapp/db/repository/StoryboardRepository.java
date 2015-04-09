package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryboardCursorParser;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TABLE_NAME;

public class StoryboardRepository {

    @Inject SqlBrite db;
    @Inject StoryboardCursorParser parser;

    @Inject
    public StoryboardRepository() { }

    public Observable<Storyboard> getByPerspective(String perspective) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PERSPECTIVE + " = ?"
                , perspective
        ).map(parser);
    }

}
