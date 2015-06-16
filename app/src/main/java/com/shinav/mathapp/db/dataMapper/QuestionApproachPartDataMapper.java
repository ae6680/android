package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.VALUE;

public class QuestionApproachPartDataMapper extends DataMapper<QuestionApproachPart> {

    @Inject
    public QuestionApproachPartDataMapper() { }

    @Override public ContentValues getContentValues(QuestionApproachPart questionApproachPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, questionApproachPart.getKey());
        values.put(QUESTION_APPROACH_KEY, questionApproachPart.getApproachKey());
        values.put(VALUE, questionApproachPart.getValue());
        values.put(POSITION, questionApproachPart.getPosition());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
