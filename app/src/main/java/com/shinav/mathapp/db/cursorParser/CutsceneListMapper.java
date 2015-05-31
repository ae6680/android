package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Cutscene;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneListMapper implements ListMapper {

    @Inject CutsceneMapper mapper;

    @Inject
    public CutsceneListMapper() { }

    @Override public List<Object> call(Query query) {
        Cursor c = query.run();
        try {
            List<Object> cutscenes = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                cutscenes.add(fromCursor(c));
            }
            return cutscenes;
        } finally {
            c.close();
        }
    }

    @Override public Cutscene fromCursor(Cursor cursor) {
        return mapper.fromCursor(cursor);
    }

}
