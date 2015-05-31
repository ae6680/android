package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.List;

import rx.functions.Func1;

public interface ListMapper extends Func1<Query, List<Object>> {
    Object fromCursor(Cursor cursor);
}
