package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.GivenApproach;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.GivenApproach.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.ARRANGEMENT;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.TABLE_NAME;

public class GivenApproachMapper {

    @Inject SqlBrite db;

    @Inject
    public GivenApproachMapper() { }

    private ContentValues getContentValues(GivenApproach givenApproach) {
        ContentValues values = new ContentValues();

        values.put(KEY, UUID.randomUUID().toString());
        values.put(APPROACH_KEY, givenApproach.getApproachKey());
        values.put(ARRANGEMENT, givenApproach.getArrangement());
        values.put(GIVEN_AT, new Date().getTime());

        return values;
    }

    public void insert(GivenApproach givenApproach) {
        db.insert(TABLE_NAME, getContentValues(givenApproach));
    }

    public void delete(String approachKey) {
        db.delete(
                TABLE_NAME,
                APPROACH_KEY + " = ?",
                approachKey
        );
    }

}
