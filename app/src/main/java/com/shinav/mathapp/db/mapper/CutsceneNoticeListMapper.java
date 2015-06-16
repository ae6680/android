package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.CutsceneNotice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.TEXT;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneNoticeListMapper extends ListMapper<CutsceneNotice> {

    @Inject
    public CutsceneNoticeListMapper() { }

    @Override public List<CutsceneNotice> call(Query query) {
        List<CutsceneNotice> cutsceneNotices = super.call(query);
        Collections.sort(cutsceneNotices);

        return cutsceneNotices;
    }

    @Override public CutsceneNotice fromCursor(Cursor c) {
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
