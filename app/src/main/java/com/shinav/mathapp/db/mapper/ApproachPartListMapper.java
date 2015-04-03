package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.ApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import static com.shinav.mathapp.db.helper.Tables.ApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ApproachPartListMapper implements rx.functions.Func1<SqlBrite.Query, List<ApproachPart>> {

    @Override public List<ApproachPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<ApproachPart> approachParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                approachParts.add(fromCursor(c));
            }
            return approachParts;
        } finally {
            c.close();
        }
     }

    public ApproachPart fromCursor(Cursor c) {
        ApproachPart approachPart = new ApproachPart();

        approachPart.setKey(c.getString(c.getColumnIndex(KEY)));
        approachPart.setApproachKey(c.getString(c.getColumnIndex(APPROACH_KEY)));
        approachPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        approachPart.setValue(c.getString(c.getColumnIndex(VALUE)));

        return approachPart;
    }

}
