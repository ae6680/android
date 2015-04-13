package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.GivenApproach;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.GivenApproach.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.GIVEN_AT;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.KEY;
import static com.shinav.mathapp.db.helper.Tables.GivenApproach.ORDER;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class GivenApproachCursorParser implements Func1<Query, GivenApproach> {

    @Inject
    public GivenApproachCursorParser() { }

    @Override public GivenApproach call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return fromCursor(c);
        } finally {
            c.close();
        }
    }

    private GivenApproach fromCursor(Cursor c) {
        GivenApproach givenApproach = new GivenApproach();

        givenApproach.setKey(c.getString(c.getColumnIndex(KEY)));
        givenApproach.setApproachKey(c.getString(c.getColumnIndex(APPROACH_KEY)));
        givenApproach.setOrder(c.getString(c.getColumnIndex(ORDER)));
        givenApproach.setGivenAt(c.getInt(c.getColumnIndex(GIVEN_AT)));

        return givenApproach;
    }

}
