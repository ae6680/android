package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;
import android.provider.ContactsContract;

import com.shinav.mathapp.db.pojo.Storyboard;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TITLE;

public class StoryboardDataMapper extends DataMapper<Storyboard> {

    @Inject
    public StoryboardDataMapper() { }

    @Override public ContentValues getContentValues(Storyboard storyboard) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyboard.getKey());
        values.put(TITLE, storyboard.getTitle());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
