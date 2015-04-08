package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.TutorialPartListCursorParser;
import com.shinav.mathapp.db.pojo.TutorialPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TUTORIAL_KEY;

public class TutorialPartRepository {

    @Inject SqlBrite db;
    @Inject TutorialPartListCursorParser parser;

    @Inject
    public TutorialPartRepository() { }

    public Observable<List<TutorialPart>> getByTutorialKey(String tutorialKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + TUTORIAL_KEY + " = ?"
                , tutorialKey
        ).map(parser);
    }

}
