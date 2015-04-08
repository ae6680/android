package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.ApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.ApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.VALUE;

public class ApproachPartMapper {

    @Inject SqlBrite db;

    @Inject
    public ApproachPartMapper() { }

    public ContentValues getContentValues(ApproachPart approachPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, approachPart.getKey());
        values.put(APPROACH_KEY, approachPart.getApproachKey());
        values.put(VALUE, approachPart.getValue());
        values.put(POSITION, approachPart.getPosition());

        return values;
    }

    public void insert(ApproachPart approachPart) {
        db.insert(TABLE_NAME, getContentValues(approachPart));
    }

}
