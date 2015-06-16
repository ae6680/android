package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.StoryboardMapper;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TABLE_NAME;

public class StoryboardRepository {

    @Inject SqlBrite db;
    @Inject StoryboardMapper mapper;

    @Inject
    public StoryboardRepository() { }

    public void findFirst(Action1<Storyboard> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " LIMIT 1"
        ).map(mapper).first().subscribe(action);
    }

    public void find(String storyboardKey, Action1<Storyboard> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , storyboardKey
        ).map(mapper).first().subscribe(action);
    }

}
