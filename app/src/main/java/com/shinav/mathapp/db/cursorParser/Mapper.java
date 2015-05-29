package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.squareup.sqlbrite.SqlBrite.Query;

import rx.functions.Func1;

public interface Mapper extends Func1<Query, Object> {
    Object fromCursor(Cursor cursor);
}
