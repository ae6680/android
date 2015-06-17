package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.QuestionApproach;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.TABLE_NAME;

public class QuestionApproachDataMapper extends DataMapper<QuestionApproach> {

    @Inject
    public QuestionApproachDataMapper() { }

    @Override public ContentValues getContentValues(QuestionApproach questionApproach) {
        ContentValues values = new ContentValues();

        values.put(KEY, questionApproach.getKey());
        values.put(QUESTION_KEY, questionApproach.getQuestionKey());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
