package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Cutscene;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneListCursorParser implements Func1<Query, List<Cutscene>> {

    @Inject CutsceneCursorParser parser;

    @Inject
    public CutsceneListCursorParser() { }

    @Override public List<Cutscene> call(Query query) {
        Cursor c = query.run();
        try {
            List<Cutscene> cutscenes = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                cutscenes.add(fromCursor(c));
            }
            return cutscenes;
        } finally {
            c.close();
        }
    }

    private Cutscene fromCursor(Cursor c) {
        return parser.fromCursor(c);
    }

}
