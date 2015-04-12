package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.StoryboardFrameMapper;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseStoryboardFrameListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final StoryboardFrameMapper storyboardFrameMapper;

    @Inject
    public FirebaseStoryboardFrameListener(FirebaseParser firebaseParser, StoryboardFrameMapper storyboardFrameMapper) {
        this.firebaseParser = firebaseParser;
        this.storyboardFrameMapper = storyboardFrameMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        StoryboardFrame storyboardFrame = firebaseParser.parseStoryboardFrame(dataSnapshot);
        storyboardFrameMapper.insert(storyboardFrame);

        Timber.d("Firebase added a StoryboardFrame");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        StoryboardFrame storyboardFrame = firebaseParser.parseStoryboardFrame(dataSnapshot);
        storyboardFrameMapper.update(storyboardFrame);

        Timber.d("Firebase changed a StoryboardFrame");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        storyboardFrameMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a StoryboardFrame");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
