package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.QuestionApproachPartListMapper;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.TABLE_NAME;

public class QuestionApproachPartRepository {

    @Inject SqlBrite db;
    @Inject QuestionApproachPartListMapper listMapper;

    @Inject
    public QuestionApproachPartRepository() { }

    public void findAllByParent(String questionApproachKey, Action1<List<QuestionApproachPart>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_APPROACH_KEY + " = ?"
                , questionApproachKey
        ).map(listMapper).first().subscribe(action);
    }

}
