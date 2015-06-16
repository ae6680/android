package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.QuestionApproachMapper;
import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.TABLE_NAME;

public class QuestionApproachRepository {

    @Inject SqlBrite db;
    @Inject QuestionApproachMapper mapper;

    @Inject
    public QuestionApproachRepository() { }

    public void findByParent(String questionKey, Action1<QuestionApproach> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        ).map(mapper).first().subscribe(action);
    }

}
