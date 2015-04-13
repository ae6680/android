package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.GivenApproachCursorParser;
import com.shinav.mathapp.db.pojo.GivenApproach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.GivenApproach.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.TABLE_NAME;

public class GivenApproachRepository {

    @Inject SqlBrite db;
    @Inject GivenApproachCursorParser parser;

    @Inject
    public GivenApproachRepository() { }

    public Observable<GivenApproach> getGivenApproachByApproachKey(String approachKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + APPROACH_KEY + " = ?"
                , approachKey
        ).map(parser);
    }

}
