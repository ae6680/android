package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.CutsceneCursorParser;
import com.shinav.mathapp.db.cursorParser.CutsceneListCursorParser;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Cutscene.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.KEY;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TITLE;

public class CutsceneRepository {

    @Inject SqlBrite db;
    @Inject CutsceneCursorParser parser;
    @Inject CutsceneListCursorParser listParser;

    @Inject
    public CutsceneRepository() { }

    public Observable<Cutscene> getByKey(String cutsceneKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , cutsceneKey
        ).map(parser);
    }

    public Observable<List<Cutscene>> getCollection(String cutsceneKeys) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT " +
                        KEY + ", " +
                        TITLE + ", " +
                        BACKGROUND_IMAGE_URL +
                        " FROM " + TABLE_NAME +
                        " WHERE " + KEY + " IN ('" + cutsceneKeys + "')"
        ).map(listParser).first();
    }

}
