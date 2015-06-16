package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.GivenQuestionApproach;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.ARRANGEMENT;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach.TABLE_NAME;

public class GivenQuestionApproachDataMapper extends DataMapper<GivenQuestionApproach> {

    @Inject
    public GivenQuestionApproachDataMapper() { }

    @Override public ContentValues getContentValues(GivenQuestionApproach givenQuestionApproach) {
        ContentValues values = new ContentValues();

        values.put(KEY, UUID.randomUUID().toString());
        values.put(QUESTION_APPROACH_KEY, givenQuestionApproach.getApproachKey());
        values.put(ARRANGEMENT, givenQuestionApproach.getArrangement());
        values.put(GIVEN_AT, new Date().getTime());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
