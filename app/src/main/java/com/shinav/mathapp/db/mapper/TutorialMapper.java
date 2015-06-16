package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Tutorial;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.KEY;
import static com.shinav.mathapp.db.helper.Tables.Tutorial.TITLE;

public class TutorialMapper extends Mapper<Tutorial> {

    @Inject
    public TutorialMapper() { }

    @Override public Tutorial fromCursor(Cursor c) {
        Tutorial tutorial = new Tutorial();

        tutorial.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorial.setTitle(c.getString(c.getColumnIndex(TITLE)));

        return tutorial;
    }
}
