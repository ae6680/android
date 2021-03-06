package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Tutorial;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TITLE;

public class TutorialMapper {

    @Inject SqlBrite db;

    @Inject
    public TutorialMapper() { }

    private ContentValues getContentValues(Tutorial tutorial) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorial.getKey());
        values.put(TITLE, tutorial.getTitle());

        return values;
    }

    public void insert(Tutorial tutorial) {
        db.insert(TABLE_NAME, getContentValues(tutorial));
    }

    public void update(Tutorial tutorial) {
        db.update(
                TABLE_NAME,
                getContentValues(tutorial),
                KEY + " = ?",
                tutorial.getKey()
        );
    }

    public void delete(String tutorialKey) {
        db.delete(TABLE_NAME, KEY + " = ?", tutorialKey);
    }

}
