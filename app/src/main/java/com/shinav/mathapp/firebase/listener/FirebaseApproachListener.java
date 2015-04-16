package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.QuestionApproachMapper;
import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseApproachListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject QuestionApproachMapper questionApproachMapper;

    @Inject
    public FirebaseApproachListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        QuestionApproach questionApproach = firebaseParser.parseApproach(dataSnapshot);
        questionApproachMapper.insert(questionApproach);

        Timber.d("Firebase added a Approach");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        QuestionApproach questionApproach = firebaseParser.parseApproach(dataSnapshot);
        questionApproachMapper.update(questionApproach);

        Timber.d("Firebase changed a Approach");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        questionApproachMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Approach");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
