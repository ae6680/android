package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Cutscene;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Cutscene.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.KEY;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TITLE;

public class CutsceneMapper  {

    @Inject SqlBrite db;

    @Inject
    public CutsceneMapper() { }

    private ContentValues getContentValues(Cutscene cutscene) {
        ContentValues values = new ContentValues();

        values.put(KEY, cutscene.getKey());
        values.put(TITLE, cutscene.getTitle());
        values.put(BACKGROUND_IMAGE_URL, cutscene.getBackgroundImageUrl());

        return values;
    }

    public void insert(Cutscene cutscene) {
        db.insert(TABLE_NAME, getContentValues(cutscene));
    }

    public void update(Cutscene cutscene) {
        db.update(
                TABLE_NAME,
                getContentValues(cutscene),
                KEY + " = ?",
                cutscene.getKey()
        );
    }

    public void delete(String cutsceneKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                cutsceneKey
        );
    }

}
