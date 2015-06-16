package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Storyboard;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.KEY;
import static com.shinav.mathapp.db.helper.Tables.Storyboard.TITLE;

public class StoryboardMapper extends Mapper<Storyboard> {

    @Inject
    public StoryboardMapper() { }

    @Override public Storyboard fromCursor(Cursor c) {
        Storyboard storyboard = new Storyboard();

        storyboard.setKey(c.getString(c.getColumnIndex(KEY)));
        storyboard.setTitle((c.getString(c.getColumnIndex(TITLE))));

        return storyboard;
    }

}
