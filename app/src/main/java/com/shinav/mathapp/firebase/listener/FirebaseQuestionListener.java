package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.QuestionMapper;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseQuestionListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject QuestionMapper questionMapper;

    @Inject
    public FirebaseQuestionListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Question question = firebaseParser.parseQuestion(dataSnapshot);
        questionMapper.insert(question);

        Timber.d("Firebase added a Question");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Question question = firebaseParser.parseQuestion(dataSnapshot);
        questionMapper.update(question);

        Timber.d("Firebase changed a Question");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        questionMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Question");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }
}
