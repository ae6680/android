package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.TutorialFrameMapper;
import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseTutorialFrameListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject TutorialFrameMapper tutorialFrameMapper;

    @Inject
    public FirebaseTutorialFrameListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        TutorialFrame tutorialFrame = firebaseParser.parseTutorialFrame(dataSnapshot);
        tutorialFrameMapper.insert(tutorialFrame);

        Timber.d("Firebase added a TutorialFrame");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        TutorialFrame tutorialFrame = firebaseParser.parseTutorialFrame(dataSnapshot);
        tutorialFrameMapper.update(tutorialFrame);

        Timber.d("Firebase changed a TutorialFrame");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        tutorialFrameMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a TutorialFrame");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
