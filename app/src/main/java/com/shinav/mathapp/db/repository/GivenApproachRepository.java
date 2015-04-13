package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.GivenApproachCursorParser;
import com.shinav.mathapp.db.pojo.GivenApproach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.GivenApproach.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.TABLE_NAME;

public class GivenApproachRepository {

    @Inject SqlBrite db;
    @Inject GivenApproachCursorParser parser;

    @Inject
    public GivenApproachRepository() { }

    public void get(String approachKey, Action1<GivenApproach> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + APPROACH_KEY + " = ?"
                , approachKey
        ).map(parser).first().subscribe(action);
    }

}
