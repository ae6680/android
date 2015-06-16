package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryboardCursorParser;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TABLE_NAME;

public class StoryboardRepository {

    @Inject SqlBrite db;
    @Inject StoryboardCursorParser parser;

    @Inject
    public StoryboardRepository() { }

    public void findFirst(Action1<Storyboard> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " LIMIT 1"
        ).map(parser).first().subscribe(action);
    }

    public void find(String storyboardKey, Action1<Storyboard> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , storyboardKey
        ).map(parser).first().subscribe(action);
    }

}
