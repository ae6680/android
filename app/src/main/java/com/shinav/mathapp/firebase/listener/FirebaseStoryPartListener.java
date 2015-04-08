package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.StoryPartMapper;
import com.shinav.mathapp.db.pojo.StoryPart;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseStoryPartListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final StoryPartMapper storyPartMapper;

    @Inject
    public FirebaseStoryPartListener(FirebaseParser firebaseParser, StoryPartMapper storyPartMapper) {
        this.firebaseParser = firebaseParser;
        this.storyPartMapper = storyPartMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        StoryPart storyPart = firebaseParser.parseStoryPart(dataSnapshot);
        storyPartMapper.insert(storyPart);
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
