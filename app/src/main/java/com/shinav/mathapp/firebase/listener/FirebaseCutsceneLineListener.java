package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.CutsceneLineMapper;
import com.shinav.mathapp.db.pojo.CutsceneLine;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseCutsceneLineListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject CutsceneLineMapper cutsceneLineMapper;

    @Inject
    public FirebaseCutsceneLineListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        CutsceneLine cutsceneLine = firebaseParser.parseCutsceneLine(dataSnapshot);
        cutsceneLineMapper.insert(cutsceneLine);

        Timber.d("Firebase added a CutsceneLine");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        CutsceneLine cutsceneLine = firebaseParser.parseCutsceneLine(dataSnapshot);
        cutsceneLineMapper.update(cutsceneLine);

        Timber.d("Firebase changed a CutsceneLine");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        cutsceneLineMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a CutsceneLine");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
