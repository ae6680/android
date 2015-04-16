package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Storyboard;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TITLE;

public class StoryboardMapper {

    @Inject SqlBrite db;

    @Inject
    public StoryboardMapper() { }

    public ContentValues getContentValues(Storyboard storyboard) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyboard.getKey());
        values.put(TITLE, storyboard.getTitle());

        return values;
    }

    public void insert(Storyboard storyboard) {
        db.insert(TABLE_NAME, getContentValues(storyboard));
    }

    public void update(Storyboard storyboard) {
        db.update(
                TABLE_NAME,
                getContentValues(storyboard),
                KEY + " = ?",
                storyboard.getKey()
        );
    }

    public void delete(String storyboardKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                storyboardKey
        );
    }

}
