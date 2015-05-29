package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Cutscene;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Cutscene.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.KEY;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneCursorParser implements Mapper {

    @Inject
    public CutsceneCursorParser() { }

    @Override public Cutscene call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return fromCursor(c);
        } finally {
            c.close();
        }
    }

    public Cutscene fromCursor(Cursor c) {
        Cutscene cutscene = new Cutscene();

        cutscene.setKey(c.getString(c.getColumnIndex(KEY)));
        cutscene.setTitle(c.getString(c.getColumnIndex(TITLE)));
        cutscene.setBackgroundImageUrl(c.getString(c.getColumnIndex(BACKGROUND_IMAGE_URL)));

        return cutscene;
    }

}
