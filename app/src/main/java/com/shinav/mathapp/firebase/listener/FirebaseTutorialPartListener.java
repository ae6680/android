package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.TutorialPartMapper;
import com.shinav.mathapp.db.pojo.TutorialPart;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseTutorialPartListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject TutorialPartMapper tutorialPartMapper;

    @Inject
    public FirebaseTutorialPartListener() {
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        TutorialPart tutorialPart = firebaseParser.parseTutorialPart(dataSnapshot);
        tutorialPartMapper.insert(tutorialPart);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(FirebaseError firebaseError) {

    }

}
