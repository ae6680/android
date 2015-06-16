package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.QuestionExplanationListMapper;
import com.shinav.mathapp.db.pojo.QuestionExplanation;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TABLE_NAME;

public class QuestionExplanationRepository {

    @Inject SqlBrite db;
    @Inject QuestionExplanationListMapper listMapper;

    @Inject
    public QuestionExplanationRepository() { }

    public void findAllByParent(String questionKey, Action1<List<QuestionExplanation>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        ).map(listMapper).first().subscribe(action);
    }

}
