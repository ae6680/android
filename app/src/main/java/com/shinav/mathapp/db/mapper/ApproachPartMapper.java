package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.ApproachPart.APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ApproachPartMapper implements rx.functions.Func1<SqlBrite.Query, List<ApproachPart>> {

    private final SqlBrite db;

    @Inject
    public ApproachPartMapper(SqlBrite db) {
        this.db = db;
    }

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

    public ContentValues getContentValues(ApproachPart approachPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, approachPart.getKey());
        values.put(APPROACH_KEY, approachPart.getApproachKey());
        values.put(VALUE, approachPart.getValue());
        values.put(POSITION, approachPart.getPosition());

        return values;
    }

    public Subscription getApproachPartsByApproachKey(String approachKey, Action1<List<ApproachPart>> action) {
        return db.createQuery(
                Tables.ApproachPart.TABLE_NAME,
                "SELECT * FROM " + Tables.ApproachPart.TABLE_NAME +
                        " WHERE " + Tables.ApproachPart.APPROACH_KEY + " = ?"
                , approachKey
        )
                .map(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    public void insert(ApproachPart approachPart) {
        db.insert(TABLE_NAME, getContentValues(approachPart));
    }

}
