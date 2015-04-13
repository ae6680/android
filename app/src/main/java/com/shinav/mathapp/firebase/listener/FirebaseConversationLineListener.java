package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.ConversationLineMapper;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseConversationLineListener implements ChildEventListener {

    @Inject FirebaseParser firebaseParser;
    @Inject ConversationLineMapper conversationLineMapper;

    @Inject
    public FirebaseConversationLineListener() { }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ConversationLine conversationLine = firebaseParser.parseConversationLine(dataSnapshot);
        conversationLineMapper.insert(conversationLine);

        Timber.d("Firebase added a ConversationLine");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ConversationLine conversationLine = firebaseParser.parseConversationLine(dataSnapshot);
        conversationLineMapper.update(conversationLine);

        Timber.d("Firebase changed a ConversationLine");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        conversationLineMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a ConversationLine");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}
