package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.GivenAnswerCursorParser;
import com.shinav.mathapp.db.pojo.GivenAnswer;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer.TABLE_NAME;

public class GivenAnswerRepository {

    @Inject SqlBrite db;
    @Inject GivenAnswerCursorParser parser;

    @Inject
    public GivenAnswerRepository() { }

    public void findByParent(String questionKey, Action1<GivenAnswer> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        ).map(parser).first().subscribe(action);
    }

}
