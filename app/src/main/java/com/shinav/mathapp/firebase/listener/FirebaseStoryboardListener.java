package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.StoryboardDataMapper;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseStoryboardListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject StoryboardDataMapper storyboardDataMapper;

    @Inject
    public FirebaseStoryboardListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Storyboard storyboard = firebaseParser.parseStoryboard(dataSnapshot);
        storyboardDataMapper.insert(storyboard);

        Timber.d("Firebase added a Storyboard");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Storyboard storyboard = firebaseParser.parseStoryboard(dataSnapshot);
        storyboardDataMapper.update(storyboard, storyboard.getKey());

        Timber.d("Firebase changed a Storyboard");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        storyboardDataMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Storyboard");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
