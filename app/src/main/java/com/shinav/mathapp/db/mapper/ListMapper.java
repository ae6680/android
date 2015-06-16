package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public abstract class ListMapper<C1> implements Func1<Query, List<C1>> {

    @Override public List<C1> call(Query query) {
        Cursor c = query.run();
        try {
            List<C1> objects = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                objects.add(fromCursor(c));
            }

            return objects;
        } finally {
            c.close();
        }
    }

    public abstract C1 fromCursor(Cursor cursor);

}
