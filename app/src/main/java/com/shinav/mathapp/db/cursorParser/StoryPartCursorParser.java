package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryPart;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.StoryPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.STORY_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryPartCursorParser implements Func1<Query, List<StoryPart>> {

    @Override public List<StoryPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<StoryPart> storyParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyParts.add(fromCursor(c));
            }
            return storyParts;
        } finally {
            c.close();
        }
    }

    public StoryPart fromCursor(Cursor c) {
        StoryPart storyPart = new StoryPart();

        storyPart.setKey(c.getString(c.getColumnIndex(KEY)));
        storyPart.setStoryKey(c.getString(c.getColumnIndex(STORY_KEY)));
        storyPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        storyPart.setType(c.getString(c.getColumnIndex(TYPE)));
        storyPart.setTypeKey(c.getString(c.getColumnIndex(TYPE_KEY)));

        return storyPart;
    }

}
