package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.CutsceneLine;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.DELAY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.MAIN_CHARACTER;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.TYPING_DURATION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneLineListMapper extends ListMapper<CutsceneLine> {

    @Inject
    public CutsceneLineListMapper() { }

    @Override public List<CutsceneLine> call(Query query) {
        List<CutsceneLine> cutsceneLines = super.call(query);
        Collections.sort(cutsceneLines);

        return cutsceneLines;
    }

    @Override public CutsceneLine fromCursor(Cursor c) {
        CutsceneLine cutsceneLine = new CutsceneLine();

        cutsceneLine.setKey(c.getString(c.getColumnIndex(KEY)));
        cutsceneLine.setCutsceneKey(c.getString(c.getColumnIndex(CUTSCENE_KEY)));
        cutsceneLine.setValue(c.getString(c.getColumnIndex(VALUE)));
        cutsceneLine.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        cutsceneLine.setDelay(c.getInt(c.getColumnIndex(DELAY)));
        cutsceneLine.setTypingDuration(c.getInt(c.getColumnIndex(TYPING_DURATION)));
        cutsceneLine.setAlignment(c.getInt(c.getColumnIndex(ALIGNMENT)));
        cutsceneLine.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));
        cutsceneLine.setMainCharacter(c.getInt(c.getColumnIndex(MAIN_CHARACTER)));

        return cutsceneLine;
    }

}
