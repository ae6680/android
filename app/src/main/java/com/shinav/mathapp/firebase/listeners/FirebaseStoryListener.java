package com.shinav.mathapp.firebase.listeners;

import com.firebase.client.DataSnapshot;

import io.realm.RealmObject;

public class FirebaseStoryListener extends FirebaseListener {

    @Override protected RealmObject parseObject(DataSnapshot dataSnapshot) {
        return firebaseParser.parseStory(dataSnapshot);
    }

    @Override protected RealmObject getObject(String dataSnapshotKey) {
        return realmRepository.getStory(dataSnapshotKey);
    }
    
}
