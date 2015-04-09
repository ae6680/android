package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ConversationLineMapper;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseConversationLineListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final ConversationLineMapper conversationLineMapper;

    @Inject
    public FirebaseConversationLineListener(
            FirebaseParser firebaseParser,
            ConversationLineMapper conversationLineMapper
    ) {
        this.firebaseParser = firebaseParser;
        this.conversationLineMapper = conversationLineMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ConversationLine conversationLine = firebaseParser.parseConversationLine(dataSnapshot);
        conversationLineMapper.insert(conversationLine);
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
