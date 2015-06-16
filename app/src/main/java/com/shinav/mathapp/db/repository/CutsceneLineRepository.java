package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.CutsceneLineCursorParser;
import com.shinav.mathapp.db.pojo.CutsceneLine;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.TABLE_NAME;

public class CutsceneLineRepository {

    @Inject SqlBrite db;
    @Inject CutsceneLineCursorParser parser;

    @Inject
    public CutsceneLineRepository() { }

    public Observable<List<CutsceneLine>> findAllByParent(String cutsceneKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + CUTSCENE_KEY + " = ?"
                , cutsceneKey
        ).map(parser);
    }

}
