package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ConversationLineMapper;
import com.shinav.mathapp.db.dataMapper.ConversationMapper;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseConversationListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject ConversationMapper conversationMapper;
    @Inject ConversationLineMapper conversationLineMapper;

    @Inject
    public FirebaseConversationListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Conversation conversation = firebaseParser.parseConversation(dataSnapshot);
        conversationMapper.insert(conversation);

        Timber.d("Firebase added a Conversation");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Conversation conversation = firebaseParser.parseConversation(dataSnapshot);
        conversationMapper.update(conversation);

        Timber.d("Firebase changed a Conversation");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        conversationMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a Conversation");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
