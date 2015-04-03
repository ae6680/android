package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.model.StoryProgressPart;

import java.util.ArrayList;
import java.util.List;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryProgressPartListMapper implements rx.functions.Func1<Query, List<StoryProgressPart>> {

    @Override public List<StoryProgressPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<StoryProgressPart> storyProgressParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyProgressParts.add(StoryProgressPart.fromCursor(c));
            }
            return storyProgressParts;
        } finally {
            c.close();
        }
    }

}
