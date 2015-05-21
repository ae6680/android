package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.QuestionCursorParser;
import com.shinav.mathapp.db.cursorParser.QuestionListCursorParser;
import com.shinav.mathapp.db.pojo.Question;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

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
    @Inject QuestionListCursorParser listParser;

    @Inject
    public QuestionRepository() { }

    public void get(String questionKey, Action1<Question> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , questionKey
        ).map(parser).first().subscribe(action);
    }

    public Observable<List<Question>> getCollection(String questionKeys) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT " +
                        KEY + ", " +
                        TITLE + ", " +
                        BACKGROUND_IMAGE_URL + ", " +
                        PROGRESS_STATE +
                        " FROM " + TABLE_NAME +
                        " WHERE " + KEY + " IN ('" + questionKeys + "')"
        ).map(listParser).first();
    }

    public Observable<Query> getAmountPassed() {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PROGRESS_STATE + " = ?"
                , String.valueOf(Question.STATE_PASSED)
        ).first();
    }

    public Observable<Query> getAmountFailed() {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + PROGRESS_STATE + " = ?"
                , String.valueOf(Question.STATE_FAILED)
        ).first();
    }

}
