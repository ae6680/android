package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Cutscene;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Cutscene.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.KEY;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TITLE;

public class CutsceneDataMapper extends DataMapper<Cutscene> {

    @Inject
    public CutsceneDataMapper() { }

    @Override public ContentValues getContentValues(Cutscene cutscene) {
        ContentValues values = new ContentValues();

        values.put(KEY, cutscene.getKey());
        values.put(TITLE, cutscene.getTitle());
        values.put(BACKGROUND_IMAGE_URL, cutscene.getBackgroundImageUrl());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}
