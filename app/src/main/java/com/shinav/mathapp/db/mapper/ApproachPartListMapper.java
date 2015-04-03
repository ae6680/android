package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.approach.ApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class ApproachPartListMapper implements rx.functions.Func1<SqlBrite.Query, List<ApproachPart>> {
    @Override public List<ApproachPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<ApproachPart> approachParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                approachParts.add(ApproachPart.fromCursor(c));
            }
            return approachParts;
        } finally {
            c.close();
        }
     }
}
