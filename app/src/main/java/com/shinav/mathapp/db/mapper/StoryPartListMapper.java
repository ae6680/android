package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.StoryPart;

import java.util.ArrayList;
import java.util.List;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryPartListMapper implements rx.functions.Func1<Query, List<StoryPart>> {
    @Override public List<StoryPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<StoryPart> storyParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyParts.add(StoryPart.fromCursor(c));
            }
            return storyParts;
        } finally {
            c.close();
        }
    }
}
