package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.CutsceneLineMapper;
import com.shinav.mathapp.db.dataMapper.CutsceneMapper;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseCutsceneListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject CutsceneMapper cutsceneMapper;
    @Inject CutsceneLineMapper cutsceneLineMapper;

    @Inject
    public FirebaseCutsceneListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Cutscene cutscene = firebaseParser.parseCutscene(dataSnapshot);
        cutsceneMapper.insert(cutscene);

        Timber.d("Firebase added a Cutscene");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Cutscene cutscene = firebaseParser.parseCutscene(dataSnapshot);
        cutsceneMapper.update(cutscene);

        Timber.d("Firebase changed a Cutscene");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        cutsceneMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Cutscene");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
