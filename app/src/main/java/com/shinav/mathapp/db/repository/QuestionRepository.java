package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.QuestionCursorParser;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.Question;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.TABLE_NAME;

public class QuestionRepository {

    @Inject SqlBrite db;
    @Inject ApproachRepository approachRepository;
    @Inject ApproachPartRepository approachPartRepository;

    @Inject
    public QuestionRepository() {
    }

    public void getByKey(String questionKey, Action1<Question> action) {

        Observable<Question> questionObservable = getByKey(questionKey);
        Observable<Approach> approachObservable = approachRepository.getApproachByQuestionKey(questionKey);

        approachObservable.map(new Func1<Approach, Object>() {
            @Override public Object call(Approach approach) {
                return approachPartRepository.getApproachPartsByApproachKey(approach.getKey());
            }
        });

        Observable.combineLatest(questionObservable, approachObservable, new Func2<Question, Approach, Question>() {
            @Override public Question call(Question question, Approach approach) {

                question.setApproach(approach);

                return question;
            }
        }).subscribe(action);
    }

    private Observable<Question> getByKey(String questionKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , questionKey
        ).map(new QuestionCursorParser());
    }

}
