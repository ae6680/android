package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.TutorialFrameListCursorParser;
import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;

public class TutorialFrameRepository {

    @Inject SqlBrite db;
    @Inject TutorialFrameListCursorParser parser;

    @Inject
    public TutorialFrameRepository() { }

    public Observable<List<TutorialFrame>> getByTutorialKey(String tutorialKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + TUTORIAL_KEY + " = ?"
                , tutorialKey
        ).map(parser);
    }

}
