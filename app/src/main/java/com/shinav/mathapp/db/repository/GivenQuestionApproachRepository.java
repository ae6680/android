package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.GivenQuestionApproachMapper;
import com.shinav.mathapp.db.pojo.GivenQuestionApproach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.TABLE_NAME;

public class GivenQuestionApproachRepository {

    @Inject SqlBrite db;
    @Inject GivenQuestionApproachMapper mapper;

    @Inject
    public GivenQuestionApproachRepository() { }

    public void findByParent(String questionApproachKey, Action1<GivenQuestionApproach> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_APPROACH_KEY + " = ?"
                , questionApproachKey
        ).map(mapper).first().subscribe(action);
    }

}
