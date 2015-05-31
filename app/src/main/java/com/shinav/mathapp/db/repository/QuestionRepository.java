package com.shinav.mathapp.db.repository;

import android.text.TextUtils;

import com.shinav.mathapp.db.cursorParser.QuestionCursorParser;
import com.shinav.mathapp.db.cursorParser.QuestionListMapper;
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
    @Inject QuestionApproachRepository questionApproachRepository;
    @Inject QuestionApproachPartRepository questionApproachPartRepository;

    @Inject QuestionCursorParser parser;
    @Inject QuestionListMapper listParser;

    @Inject
    public QuestionRepository() { }

    public void find(String questionKey, Action1<Question> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , questionKey
        ).map(parser).first().subscribe(action);
    }

    public Observable<List<Object>> findCollection(List<String> questionKeys) {

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
                .map(listParser)
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
