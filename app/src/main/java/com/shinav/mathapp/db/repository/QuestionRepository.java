package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.QuestionCursorParser;
import com.shinav.mathapp.db.cursorParser.QuestionListCursorParser;
import com.shinav.mathapp.db.pojo.Question;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
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

//    public void get(String questionKey, Action1<Question> action) {
//
//        Observable<Question> questionObservable = get(questionKey);
//        Observable<Approach> approachObservable = approachRepository.get(questionKey);
//
//        Observable.combineLatest(questionObservable, approachObservable, new Func2<Question, Approach, Question>() {
//            @Override public Question call(Question question, Approach approach) {
//
//                question.setApproach(approach);
//
//                return question;
//            }
//        })
//                .map(new Func1<Question, Object>() {
//                    @Override public Question call(final Question question) {
//
//                        approachPartRepository.getApproachParts(question.get().getKey()).first().map(new Func1<List<ApproachPart>, Object>() {
//                            @Override public Object call(List<ApproachPart> approachParts) {
//
//                                question.get().setApproachParts(approachParts);
//
//                                return null;
//                            }
//                        });
//
//                        return question;
//
//                    }
//                })
//                .subscribe(action);
//
//    }

    public void get(String questionKey, Action1<Question> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , questionKey
        ).map(parser).first().subscribe(action);
    }

    public void getCollection(String questionKeys, Action1<List<Question>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT " +
                        KEY + ", " + TITLE + ", " + ANSWER + " FROM " + TABLE_NAME +
                        " WHERE " + KEY + " IN ('" + questionKeys + "')"
        ).map(listParser).first().subscribe(action);
    }
}
