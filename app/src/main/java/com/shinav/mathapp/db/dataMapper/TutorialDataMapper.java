package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TITLE;

public class TutorialDataMapper extends DataMapper<Tutorial> {

    @Inject
    public TutorialDataMapper() { }

    @Override public ContentValues getContentValues(Tutorial tutorial) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorial.getKey());
        values.put(TITLE, tutorial.getTitle());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
