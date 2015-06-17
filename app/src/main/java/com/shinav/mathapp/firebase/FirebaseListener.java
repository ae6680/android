package com.shinav.mathapp.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.DataMapper;
import com.shinav.mathapp.firebase.mapper.FirebaseMapper;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseListener<FBMapper extends FirebaseMapper, DMapper extends DataMapper> implements ChildEventListener {

    private final FBMapper firebaseMapper;
    private final DMapper dataMapper;

    @Inject
    public FirebaseListener(FBMapper firebaseMapper, DMapper dataMapper) {
        this.firebaseMapper = firebaseMapper;
        this.dataMapper = dataMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        dataMapper.insert(firebaseMapper.fromDataSnapshot(dataSnapshot));
        Timber.d("%s inserted one object", dataMapper.getClass().getSimpleName());
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        dataMapper.update(firebaseMapper.fromDataSnapshot(dataSnapshot), dataSnapshot.getKey());
        Timber.d("%s updated one object", dataMapper.getClass().getSimpleName());
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        dataMapper.delete(dataSnapshot.getKey());
        Timber.d("%s removed one object", dataMapper.getClass().getSimpleName());
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

    @Override public void onCancelled(FirebaseError firebaseError) { }

}
