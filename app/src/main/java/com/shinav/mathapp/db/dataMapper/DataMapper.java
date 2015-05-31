package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

public interface DataMapper {
    void insert(Object object);
    void update(Object object);
    void delete(String objectKey);
    ContentValues getContentValues(Object object);
}
