package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.CutsceneLineDataMapper;
import com.shinav.mathapp.db.dataMapper.CutsceneDataMapper;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseCutsceneListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject CutsceneDataMapper cutsceneDataMapper;
    @Inject CutsceneLineDataMapper cutsceneLineDataMapper;

    @Inject
    public FirebaseCutsceneListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Cutscene cutscene = firebaseParser.parseCutscene(dataSnapshot);
        cutsceneDataMapper.insert(cutscene);

        Timber.d("Firebase added a Cutscene");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Cutscene cutscene = firebaseParser.parseCutscene(dataSnapshot);
        cutsceneDataMapper.update(cutscene, cutscene.getKey());

        Timber.d("Firebase changed a Cutscene");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        cutsceneDataMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Cutscene");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
