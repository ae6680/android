package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.TutorialCursorParser;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;

public class TutorialRepository {

    @Inject SqlBrite db;
    @Inject TutorialCursorParser parser;

    @Inject
    public TutorialRepository() { }

    public Observable<Tutorial> getByPerspective(String perspective) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PERSPECTIVE + " = ?"
                , perspective
        ).map(parser);
    };

}
