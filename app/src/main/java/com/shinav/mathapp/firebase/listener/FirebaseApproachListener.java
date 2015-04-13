package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ApproachMapper;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseApproachListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject ApproachMapper approachMapper;

    @Inject
    public FirebaseApproachListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Approach approach = firebaseParser.parseApproach(dataSnapshot);
        approachMapper.insert(approach);

        Timber.d("Firebase added a Approach");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Approach approach = firebaseParser.parseApproach(dataSnapshot);
        approachMapper.update(approach);

        Timber.d("Firebase changed a Approach");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        approachMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Approach");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
