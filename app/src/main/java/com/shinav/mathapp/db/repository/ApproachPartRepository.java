package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ApproachPartListCursorParser;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.ApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.TABLE_NAME;

public class ApproachPartRepository {

    @Inject SqlBrite db;
    @Inject ApproachPartListCursorParser parser;

    @Inject
    public ApproachPartRepository() { }

    public Observable<List<ApproachPart>> getApproachPartsByApproachKey(String approachKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + APPROACH_KEY + " = ?"
                , approachKey
        ).map(parser);
    }

}
