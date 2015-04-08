package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryProgressPart;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryProgressPartListCursorParser implements Func1<Query, List<StoryProgressPart>> {

    @Inject StoryProgressPartCursorParser parser;

    @Inject
    public StoryProgressPartListCursorParser() {
    }

    @Override public List<StoryProgressPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<StoryProgressPart> storyProgressParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyProgressParts.add(fromCursor(c));
            }
            return storyProgressParts;
        } finally {
            c.close();
        }
    }

    private StoryProgressPart fromCursor(Cursor c) {
        return parser.fromCursor(c);
    }

}
