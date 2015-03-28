package com.shinav.mathapp.firebase.listeners;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.repository.RealmRepository;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmObject;

public class FirebaseStoryListener extends FirebaseListener {

    private final FirebaseParser firebaseParser;
    private final Realm realm;
    private final RealmRepository realmRepository;

    @Inject
    public FirebaseStoryListener(
            FirebaseParser firebaseParser,
            Realm realm,
            RealmRepository realmRepository
    ) {
        this.firebaseParser = firebaseParser;
        this.realm = realm;
        this.realmRepository = realmRepository;
    }

    @Override public RealmObject parseObject(DataSnapshot dataSnapshot) {
        return firebaseParser.parseStory(dataSnapshot);
    }

    @Override public RealmObject getObject(String dataSnapshotKey) {
        return realmRepository.getStory(dataSnapshotKey);
    }

    @Override public Realm getRealm() {
        return realm;
    }

}
