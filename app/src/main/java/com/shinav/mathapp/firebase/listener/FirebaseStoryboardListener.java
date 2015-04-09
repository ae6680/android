package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.StoryboardMapper;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseStoryboardListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final StoryboardMapper storyboardMapper;

    @Inject
    public FirebaseStoryboardListener(FirebaseParser firebaseParser, StoryboardMapper storyboardMapper) {
        this.firebaseParser = firebaseParser;
        this.storyboardMapper = storyboardMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Storyboard storyboard = firebaseParser.parseStoryboard(dataSnapshot);
        storyboardMapper.insert(storyboard);
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
