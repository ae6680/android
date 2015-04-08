package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Story;

import static com.shinav.mathapp.db.helper.Tables.Story.KEY;

public class StoryCursorParser {

    public Story fromCursor(Cursor c) {
        Story story = new Story();

        story.setKey(c.getString(c.getColumnIndex(KEY)));

        return story;
    }
}
