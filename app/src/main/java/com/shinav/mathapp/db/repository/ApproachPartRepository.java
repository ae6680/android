package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ApproachPartListCursorParser;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.ApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.TABLE_NAME;

public class ApproachPartRepository {

    @Inject SqlBrite db;
    @Inject ApproachPartListCursorParser parser;

    @Inject
    public ApproachPartRepository() { }

    public void getApproachParts(String approachKey, Action1<List<ApproachPart>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + APPROACH_KEY + " = ?"
                , approachKey
        ).map(parser).first().subscribe(action);
    }

}
