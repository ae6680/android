package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.mapper.TutorialMapper;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseTutorialListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject TutorialMapper tutorialMapper;

    @Inject
    public FirebaseTutorialListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Tutorial tutorial = firebaseParser.parseTutorial(dataSnapshot);
        tutorialMapper.insert(tutorial);
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
