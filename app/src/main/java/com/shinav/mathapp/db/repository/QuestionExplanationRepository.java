package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.QuestionExplanationCursorParser;
import com.shinav.mathapp.db.pojo.QuestionExplanation;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TABLE_NAME;

public class QuestionExplanationRepository {

    @Inject SqlBrite db;
    @Inject QuestionExplanationCursorParser parser;

    @Inject
    public QuestionExplanationRepository() { }

    public void get(String questionKey, Action1<List<QuestionExplanation>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        ).map(parser).first().subscribe(action);
    }

}
