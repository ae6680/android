package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Approach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Approach.KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.TABLE_NAME;

public class ApproachMapper {

    @Inject SqlBrite db;

    @Inject
    public ApproachMapper() { }

    public ContentValues getContentValues(Approach approach) {
        ContentValues values = new ContentValues();

        values.put(KEY, approach.getKey());
        values.put(QUESTION_KEY, approach.getQuestionKey());

        return values;
    }

    public void insert(Approach approach) {
        db.insert(TABLE_NAME, getContentValues(approach));
    }

}
