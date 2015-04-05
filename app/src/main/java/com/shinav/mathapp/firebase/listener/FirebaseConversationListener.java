package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.mapper.ConversationMapper;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseConversationListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final ConversationMapper conversationMapper;

    @Inject
    public FirebaseConversationListener(
            FirebaseParser firebaseParser,
            ConversationMapper conversationMapper
    ) {
        this.firebaseParser = firebaseParser;
        this.conversationMapper = conversationMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Conversation conversation = firebaseParser.parseConversation(dataSnapshot);
        conversationMapper.insert(conversation);
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
