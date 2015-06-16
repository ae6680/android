package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.QuestionExplanationDataMapper;
import com.shinav.mathapp.db.pojo.QuestionExplanation;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseQuestionExplanationListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject QuestionExplanationDataMapper questionExplanationDataMapper;

    @Inject
    public FirebaseQuestionExplanationListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        QuestionExplanation questionExplanation = firebaseParser.parseQuestionExplanation(dataSnapshot);
        questionExplanationDataMapper.insert(questionExplanation);

        Timber.d("Firebase added a QuestionExplanation");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        QuestionExplanation questionExplanation = firebaseParser.parseQuestionExplanation(dataSnapshot);
        questionExplanationDataMapper.update(questionExplanation, questionExplanation.getKey());

        Timber.d("Firebase changed a QuestionExplanation");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        questionExplanationDataMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a QuestionExplanation");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
