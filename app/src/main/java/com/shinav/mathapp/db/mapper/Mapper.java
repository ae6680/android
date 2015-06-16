package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.squareup.sqlbrite.SqlBrite.Query;

import rx.functions.Func1;

public abstract class Mapper<C1> implements Func1<Query, C1> {

    @Override public C1 call(Query query) {
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

    public abstract C1 fromCursor(Cursor cursor);

}
