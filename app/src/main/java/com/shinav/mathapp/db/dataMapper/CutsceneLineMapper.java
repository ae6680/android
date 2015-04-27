package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.CutsceneLine;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.DELAY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.MAIN_CHARACTER;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.TYPING_DURATION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.VALUE;

public class CutsceneLineMapper {

    @Inject SqlBrite db;

    @Inject
    public CutsceneLineMapper() { }

    private ContentValues getContentValues(CutsceneLine cutsceneLine) {
        ContentValues values = new ContentValues();

        values.put(KEY, cutsceneLine.getKey());
        values.put(POSITION, cutsceneLine.getPosition());
        values.put(ALIGNMENT, cutsceneLine.getAlignment());
        values.put(DELAY, cutsceneLine.getDelay());
        values.put(TYPING_DURATION, cutsceneLine.getTypingDuration());
        values.put(CUTSCENE_KEY, cutsceneLine.getCutsceneKey());
        values.put(VALUE, cutsceneLine.getValue());
        values.put(IMAGE_URL, cutsceneLine.getImageUrl());
        values.put(MAIN_CHARACTER, cutsceneLine.getMainCharacter());

        return values;
    }

    public void insert(CutsceneLine cutsceneLine) {
        db.insert(TABLE_NAME, getContentValues(cutsceneLine));
    }

    public void update(CutsceneLine cutsceneLine) {
        db.update(
                TABLE_NAME,
                getContentValues(cutsceneLine),
                KEY + " = ?",
                cutsceneLine.getKey()
        );
    }

    public void delete(String cutsceneLineKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                cutsceneLineKey
        );
    }

}
