package com.shinav.mathapp.db.repository;

import android.text.TextUtils;

import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.db.mapper.QuestionListMapper;
import com.shinav.mathapp.db.pojo.Question;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.shinav.mathapp.db.helper.Tables.Question.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.PROGRESS_STATE;
import static com.shinav.mathapp.db.helper.Tables.Question.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;

public class QuestionRepository {

    @Inject SqlBrite db;
    @Inject QuestionMapper mapper;
    @Inject QuestionListMapper listMapper;

    @Inject
    public QuestionRepository() { }

    public void find(String questionKey, Action1<Question> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , questionKey
        ).map(mapper).first().subscribe(action);
    }

    public Observable<List<Question>> findCollection(List<String> questionKeys) {

        String questionKeysString = TextUtils.join("','", questionKeys);

        return db.createQuery(
                TABLE_NAME,
                "SELECT " +
                        KEY + ", " +
                        TITLE + ", " +
                        BACKGROUND_IMAGE_URL + ", " +
                        PROGRESS_STATE +
                        " FROM " + TABLE_NAME +
                        " WHERE " + KEY + " IN ('" + questionKeysString + "')"
        )
                .map(listMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first();
    }

    public Observable<Query> findAmountPassed() {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PROGRESS_STATE + " = ?"
                , String.valueOf(Question.STATE_PASSED)
        ).first();
    }

    public Observable<Query> findAmountFailed() {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PROGRESS_STATE + " = ?"
                , String.valueOf(Question.STATE_FAILED)
        ).first();
    }

}
