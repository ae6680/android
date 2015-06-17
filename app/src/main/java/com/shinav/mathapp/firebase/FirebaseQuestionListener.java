package com.shinav.mathapp.firebase;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.dataMapper.QuestionDataMapper;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.firebase.mapper.QuestionFirebaseMapper;

import javax.inject.Inject;

import rx.functions.Action1;
import timber.log.Timber;

public class FirebaseQuestionListener extends FirebaseListener<QuestionFirebaseMapper, QuestionDataMapper> {

    private QuestionFirebaseMapper questionFirebaseMapper;
    private QuestionDataMapper questionDataMapper;
    private QuestionRepository repository;

    @Inject
    public FirebaseQuestionListener(
        QuestionFirebaseMapper questionFirebaseMapper,
        QuestionDataMapper questionDataMapper,
        QuestionRepository repository
    ) {
        super(questionFirebaseMapper, questionDataMapper);
        this.questionFirebaseMapper = questionFirebaseMapper;
        this.questionDataMapper = questionDataMapper;
        this.repository = repository;
    }

    public FirebaseQuestionListener(QuestionFirebaseMapper firebaseMapper, QuestionDataMapper dataMapper) {
        super(firebaseMapper, dataMapper);
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        final Question question = questionFirebaseMapper.fromDataSnapshot(dataSnapshot);

        // Look for previous progressState
        repository.find(question.getKey(), new Action1<Question>() {
            @Override public void call(Question q) {
                if (q != null) {
                    question.setProgressState(q.getProgressState());
                }
                questionDataMapper.insert(question);
                Timber.d("Firebase added a Question");
            }
        });

    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        final Question question = questionFirebaseMapper.fromDataSnapshot(dataSnapshot);

        // Look for previous progressState
        repository.find(question.getKey(), new Action1<Question>() {
            @Override public void call(Question q) {
                if (q != null) {
                    question.setProgressState(q.getProgressState());
                }
                questionDataMapper.update(question, question.getKey());
                Timber.d("Firebase changed a Question");
            }
        });

    }

}
