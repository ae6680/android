package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.conversation.Conversation;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

public class FirebaseConversationListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final SqlBrite db;

    @Inject
    public FirebaseConversationListener(
            FirebaseParser firebaseParser,
            SqlBrite db
    ) {
        this.firebaseParser = firebaseParser;
        this.db = db;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Conversation conversation = firebaseParser.parseConversation(dataSnapshot);
        db.insert(Tables.Conversation.TABLE_NAME, conversation.getContentValues());
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
