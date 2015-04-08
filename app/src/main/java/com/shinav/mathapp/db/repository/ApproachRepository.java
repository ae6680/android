package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ApproachCursorParser;
import com.shinav.mathapp.db.pojo.Approach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Approach.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.TABLE_NAME;

public class ApproachRepository {

    @Inject SqlBrite db;
    @Inject ApproachCursorParser parser;

    @Inject
    public ApproachRepository() { }

    public Observable<Approach> getApproachByQuestionKey(String questionKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        ).map(parser);
    }

}
