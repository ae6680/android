package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ApproachPartMapper;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseApproachPartListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final ApproachPartMapper approachPartMapper;

    @Inject
    public FirebaseApproachPartListener(FirebaseParser firebaseParser, ApproachPartMapper approachPartMapper) {
        this.firebaseParser = firebaseParser;
        this.approachPartMapper = approachPartMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ApproachPart approachPart = firebaseParser.parseApproachPart(dataSnapshot);
        approachPartMapper.insert(approachPart);
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
