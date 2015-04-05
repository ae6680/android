package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Approach;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Approach.KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.Approach.TABLE_NAME;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ApproachMapper implements rx.functions.Func1<Query, Approach> {

    private final SqlBrite db;

    @Inject
    public ApproachMapper(SqlBrite db) {
        this.db = db;
    }

    @Override public Approach call(Query query) {
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

    public Approach fromCursor(Cursor c) {
        Approach approach = new Approach();

        approach.setKey(c.getString(c.getColumnIndex(KEY)));
        approach.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));

        return approach;
    }

    public ContentValues getContentValues(Approach approach) {
        ContentValues values = new ContentValues();

        values.put(KEY, approach.getKey());
        values.put(QUESTION_KEY, approach.getQuestionKey());

        return values;
    }

    public Subscription getApproachByQuestionKey(String questionKey, Action1<Approach> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        )
                .map(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    public void insert(Approach approach) {
        db.insert(TABLE_NAME, getContentValues(approach));
    }
}
