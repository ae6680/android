package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.VALUE;

public class QuestionApproachPartMapper {

    @Inject SqlBrite db;

    @Inject
    public QuestionApproachPartMapper() { }

    private ContentValues getContentValues(QuestionApproachPart questionApproachPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, questionApproachPart.getKey());
        values.put(APPROACH_KEY, questionApproachPart.getApproachKey());
        values.put(VALUE, questionApproachPart.getValue());
        values.put(POSITION, questionApproachPart.getPosition());

        return values;
    }

    public void insert(QuestionApproachPart questionApproachPart) {
        db.insert(TABLE_NAME, getContentValues(questionApproachPart));
    }

    public void update(QuestionApproachPart questionApproachPart) {
        db.update(
                TABLE_NAME,
                getContentValues(questionApproachPart),
                KEY + " = ?",
                questionApproachPart.getKey()
        );
    }

    public void delete(String approachPartKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                approachPartKey
        );
    }

}
