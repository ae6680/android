package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.CutsceneNoticeListMapper;
import com.shinav.mathapp.db.pojo.CutsceneNotice;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.TABLE_NAME;

public class CutsceneNoticeRepository {

    @Inject SqlBrite db;
    @Inject CutsceneNoticeListMapper listMapper;

    @Inject
    public CutsceneNoticeRepository() { }

    public Observable<List<CutsceneNotice>> findAllByParent(String cutsceneKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + CUTSCENE_KEY + " = ?"
                , cutsceneKey
        ).map(listMapper);
    }

}
