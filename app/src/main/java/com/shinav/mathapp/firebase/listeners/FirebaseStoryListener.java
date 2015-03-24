package com.shinav.mathapp.firebase.listeners;

import com.firebase.client.DataSnapshot;

import io.realm.RealmObject;

public class FirebaseStoryListener extends FirebaseListener {

    @Override public RealmObject parseObject(DataSnapshot dataSnapshot) {
        return firebaseParser.parseStory(dataSnapshot);
    }

    @Override public RealmObject getObject(String dataSnapshotKey) {
        return realmRepository.getStory(dataSnapshotKey);
    }

}
