package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.QuestionMapper;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import rx.functions.Action1;
import timber.log.Timber;

public class FirebaseQuestionListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject QuestionMapper questionMapper;
    @Inject QuestionRepository repository;

    @Inject
    public FirebaseQuestionListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        final Question question = firebaseParser.parseQuestion(dataSnapshot);

        // Look for previous progressState
        repository.find(question.getKey(), new Action1<Question>() {
            @Override public void call(Question q) {
                if (q != null) {
                    question.setProgressState(q.getProgressState());
                }
                questionMapper.insert(question);
                Timber.d("Firebase added a Question");
            }
        });

    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        final Question question = firebaseParser.parseQuestion(dataSnapshot);

        // Look for previous progressState
        repository.find(question.getKey(), new Action1<Question>() {
            @Override public void call(Question q) {
                if (q != null) {
                    question.setProgressState(q.getProgressState());
                }
                questionMapper.update(question);
                Timber.d("Firebase changed a Question");
            }
        });

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        questionMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Question");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }
}
