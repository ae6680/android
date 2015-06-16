package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

public abstract class DataMapper<C1> implements IDataMapper<C1> {

    @Inject SqlBrite db;

    @Override public void insert(C1 object) {
        db.insert(getTable(), getContentValues(object));
    }

    @Override public void update(C1 object, String objectKey) {
        db.update(
            getTable(),
            getContentValues(object),
            "key = ?",
            objectKey
        );
    }

    @Override public void delete(String objectKey) {
        db.delete(getTable(), "key = ?", objectKey);
    }

    public abstract ContentValues getContentValues(C1 object);
    public abstract String getTable();

}
