package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.CutsceneNotice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.TEXT;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneNoticeCursorParser implements Func1<Query, List<CutsceneNotice>> {

    @Inject
    public CutsceneNoticeCursorParser() { }

    @Override public List<CutsceneNotice> call(Query query) {
        Cursor c = query.run();
        try {
            List<CutsceneNotice> cutsceneNotices = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                cutsceneNotices.add(fromCursor(c));
            }

            Collections.sort(cutsceneNotices);
            return cutsceneNotices;
        } finally {
            c.close();
        }
    }

    public CutsceneNotice fromCursor(Cursor c) {
        CutsceneNotice cutsceneNotice = new CutsceneNotice();

        cutsceneNotice.setKey(c.getString(c.getColumnIndex(KEY)));
        cutsceneNotice.setCutsceneKey(c.getString(c.getColumnIndex(CUTSCENE_KEY)));
        cutsceneNotice.setText(c.getString(c.getColumnIndex(TEXT)));
        cutsceneNotice.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        cutsceneNotice.setAlignment(c.getInt(c.getColumnIndex(ALIGNMENT)));
        cutsceneNotice.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));

        return cutsceneNotice;
    }

}
