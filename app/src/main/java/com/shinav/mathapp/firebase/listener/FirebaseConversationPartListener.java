package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ConversationPartMapper;
import com.shinav.mathapp.db.pojo.ConversationPart;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseConversationPartListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final ConversationPartMapper conversationPartMapper;

    @Inject
    public FirebaseConversationPartListener(
            FirebaseParser firebaseParser,
            ConversationPartMapper conversationPartMapper
    ) {
        this.firebaseParser = firebaseParser;
        this.conversationPartMapper = conversationPartMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ConversationPart conversationPart = firebaseParser.parseConversationPart(dataSnapshot);
        conversationPartMapper.insert(conversationPart);
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
