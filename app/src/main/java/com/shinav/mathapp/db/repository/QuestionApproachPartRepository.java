package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.QuestionApproachPartListCursorParser;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.TABLE_NAME;

public class QuestionApproachPartRepository {

    @Inject SqlBrite db;
    @Inject QuestionApproachPartListCursorParser parser;

    @Inject
    public QuestionApproachPartRepository() { }

    public void getApproachParts(String approachKey, Action1<List<QuestionApproachPart>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_APPROACH_KEY + " = ?"
                , approachKey
        ).map(parser).first().subscribe(action);
    }

}
