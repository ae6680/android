package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.QuestionApproach;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.QUESTION_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionApproachMapper extends Mapper<QuestionApproach> {

    @Inject
    public QuestionApproachMapper() { }

    @Override public QuestionApproach fromCursor(Cursor c) {
        QuestionApproach questionApproach = new QuestionApproach();

        questionApproach.setKey(c.getString(c.getColumnIndex(KEY)));
        questionApproach.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));

        return questionApproach;
    }

}
