package com.shinav.mathapp.db.repository;

import android.text.TextUtils;

import com.shinav.mathapp.db.mapper.CutsceneListMapper;
import com.shinav.mathapp.db.mapper.CutsceneMapper;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.shinav.mathapp.db.helper.Tables.Cutscene.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.KEY;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TITLE;

public class CutsceneRepository {

    @Inject SqlBrite db;
    @Inject CutsceneMapper mapper;
    @Inject CutsceneListMapper listMapper;

    @Inject
    public CutsceneRepository() { }

    public void find(String cutsceneKey, Action1<Object> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , cutsceneKey
        )
                .map(mapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first().subscribe(action);
    }

    public Observable<List<Cutscene>> findCollection(List<String> cutsceneKeys) {

        String cutsceneKeysString = TextUtils.join("','", cutsceneKeys);

        return db.createQuery(
                TABLE_NAME,
                "SELECT " +
                        KEY + ", " +
                        TITLE + ", " +
                        BACKGROUND_IMAGE_URL +
                        " FROM " + TABLE_NAME +
                        " WHERE " + KEY + " IN ('" + cutsceneKeysString + "')"
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first()
                .map(listMapper);
    }

}
