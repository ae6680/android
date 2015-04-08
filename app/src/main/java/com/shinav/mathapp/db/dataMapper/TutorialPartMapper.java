package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.TutorialPart;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TUTORIAL_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TYPE_KEY;

public class TutorialPartMapper {

    @Inject SqlBrite db;

    @Inject
    public TutorialPartMapper() {
    }

    public void insert(TutorialPart tutorialPart) {
        db.insert(TABLE_NAME, getContentValues(tutorialPart));
    }

    private ContentValues getContentValues(TutorialPart tutorialPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorialPart.getKey());
        values.put(TUTORIAL_KEY, tutorialPart.getTutorialKey());
        values.put(TYPE, tutorialPart.getType());
        values.put(TYPE_KEY, tutorialPart.getTypeKey());
        values.put(POSITION, tutorialPart.getPosition());

        return values;
    }

}
