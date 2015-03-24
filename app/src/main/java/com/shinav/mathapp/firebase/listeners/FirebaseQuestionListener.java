package com.shinav.mathapp.firebase.listeners;

import com.firebase.client.DataSnapshot;

import io.realm.RealmObject;

public class FirebaseQuestionListener extends FirebaseListener {

    @Override public RealmObject parseObject(DataSnapshot dataSnapshot) {
        return firebaseParser.parseQuestion(dataSnapshot);
    }

    @Override public RealmObject getObject(String dataSnapshotKey) {
        return realmRepository.getQuestion(dataSnapshotKey);
    }

}
