package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Storyboard;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TABLE_NAME;

public class StoryboardMapper {

    @Inject SqlBrite db;

    @Inject
    public StoryboardMapper() { }

    public ContentValues getContentValues(Storyboard storyboard) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyboard.getKey());

        return values;
    }

    public void insert(Storyboard storyboard) {
        db.insert(TABLE_NAME, getContentValues(storyboard));
    }

}
