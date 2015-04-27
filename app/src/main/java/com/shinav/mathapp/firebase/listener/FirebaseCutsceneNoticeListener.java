package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.CutsceneNoticeMapper;
import com.shinav.mathapp.db.pojo.CutsceneNotice;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseCutsceneNoticeListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject CutsceneNoticeMapper cutsceneNoticeMapper;

    @Inject
    public FirebaseCutsceneNoticeListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        CutsceneNotice cutsceneNotice = firebaseParser.parseCutsceneNotice(dataSnapshot);
        cutsceneNoticeMapper.insert(cutsceneNotice);

        Timber.d("Firebase added a CutsceneNotice");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        CutsceneNotice cutsceneNotice = firebaseParser.parseCutsceneNotice(dataSnapshot);
        cutsceneNoticeMapper.update(cutsceneNotice);

        Timber.d("Firebase changed a CutsceneNotice");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        cutsceneNoticeMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a CutsceneNotice");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
