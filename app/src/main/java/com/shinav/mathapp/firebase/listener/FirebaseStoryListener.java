package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.StoryMapper;
import com.shinav.mathapp.db.pojo.Story;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseStoryListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final StoryMapper storyMapper;

    @Inject
    public FirebaseStoryListener(FirebaseParser firebaseParser, StoryMapper storyMapper) {
        this.firebaseParser = firebaseParser;
        this.storyMapper = storyMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Story story = firebaseParser.parseStory(dataSnapshot);
        storyMapper.insert(story);
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
