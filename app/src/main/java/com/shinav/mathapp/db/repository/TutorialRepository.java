package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.TutorialMapper;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;

public class TutorialRepository {

    @Inject SqlBrite db;
    @Inject TutorialMapper mapper;

    @Inject
    public TutorialRepository() { }

    public void findFirst(Action1<Tutorial> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " LIMIT 1"
        ).map(mapper).first().subscribe(action);
    }

    public void find(String tutorialKey, Action1<Tutorial> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , tutorialKey
        ).map(mapper).first().subscribe(action);
    }

}
