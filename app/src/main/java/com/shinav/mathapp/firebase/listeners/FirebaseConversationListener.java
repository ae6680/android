package com.shinav.mathapp.firebase.listeners;

import com.firebase.client.DataSnapshot;

import io.realm.RealmObject;

public class FirebaseConversationListener extends FirebaseListener {

    @Override public RealmObject parseObject(DataSnapshot dataSnapshot) {
        return firebaseParser.parseConversation(dataSnapshot);
    }

    @Override public RealmObject getObject(String dataSnapshotKey) {
        return realmRepository.getConversation(dataSnapshotKey);
    }

}
