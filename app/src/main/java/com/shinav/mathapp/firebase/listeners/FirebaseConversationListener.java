package com.shinav.mathapp.firebase.listeners;

import com.firebase.client.DataSnapshot;

import io.realm.RealmObject;

public class FirebaseConversationListener extends FirebaseListener {

    @Override protected RealmObject parseObject(DataSnapshot dataSnapshot) {
        return firebaseParser.parseConversation(dataSnapshot);
    }

    @Override protected RealmObject getObject(String dataSnapshotKey) {
        return realmRepository.getConversation(dataSnapshotKey);
    }

}
