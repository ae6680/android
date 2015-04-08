package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.PERSPECTIVE;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;

public class TutorialMapper {

    @Inject SqlBrite db;

    @Inject
    public TutorialMapper() { }

    private ContentValues getContentValues(Tutorial tutorial) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorial.getKey());
        values.put(PERSPECTIVE, tutorial.getPerspective());

        return values;
    }

    public void insert(Tutorial tutorial) {
        db.insert(TABLE_NAME, getContentValues(tutorial));
    }

}
