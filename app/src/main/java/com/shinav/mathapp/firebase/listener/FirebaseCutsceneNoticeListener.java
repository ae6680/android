package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.CutsceneNoticeDataMapper;
import com.shinav.mathapp.db.pojo.CutsceneNotice;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseCutsceneNoticeListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject CutsceneNoticeDataMapper cutsceneNoticeDataMapper;

    @Inject
    public FirebaseCutsceneNoticeListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        CutsceneNotice cutsceneNotice = firebaseParser.parseCutsceneNotice(dataSnapshot);
        cutsceneNoticeDataMapper.insert(cutsceneNotice);

        Timber.d("Firebase added a CutsceneNotice");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        CutsceneNotice cutsceneNotice = firebaseParser.parseCutsceneNotice(dataSnapshot);
        cutsceneNoticeDataMapper.update(cutsceneNotice, cutsceneNotice.getKey());

        Timber.d("Firebase changed a CutsceneNotice");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        cutsceneNoticeDataMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a CutsceneNotice");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
