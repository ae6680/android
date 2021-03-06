package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.CutsceneNotice;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.TEXT;

public class CutsceneNoticeMapper {

    @Inject SqlBrite db;

    @Inject
    public CutsceneNoticeMapper() { }

    private ContentValues getContentValues(CutsceneNotice cutsceneNotice) {
        ContentValues values = new ContentValues();

        values.put(KEY, cutsceneNotice.getKey());
        values.put(CUTSCENE_KEY, cutsceneNotice.getCutsceneKey());
        values.put(POSITION, cutsceneNotice.getPosition());
        values.put(ALIGNMENT, cutsceneNotice.getAlignment());
        values.put(TEXT, cutsceneNotice.getText());
        values.put(IMAGE_URL, cutsceneNotice.getImageUrl());

        return values;
    }

    public void insert(CutsceneNotice cutsceneNotice) {
        db.insert(TABLE_NAME, getContentValues(cutsceneNotice));
    }

    public void update(CutsceneNotice cutsceneNotice) {
        db.update(
                TABLE_NAME,
                getContentValues(cutsceneNotice),
                KEY + " = ?",
                cutsceneNotice.getKey()
        );
    }

    public void delete(String cutsceneNoticeKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                cutsceneNoticeKey
        );
    }
}
