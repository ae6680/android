package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ApproachMapper;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseApproachListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final ApproachMapper approachMapper;

    @Inject
    public FirebaseApproachListener(FirebaseParser firebaseParser, ApproachMapper approachMapper) {
        this.firebaseParser = firebaseParser;
        this.approachMapper = approachMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Approach approach = firebaseParser.parseApproach(dataSnapshot);
        approachMapper.insert(approach);
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
