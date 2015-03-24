package com.shinav.mathapp.firebase.listeners;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.repository.RealmRepository;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class FirebaseListener implements ChildEventListener {

    public static final String TAG = "FirebaseListener";

    @Inject Realm realm;
    @Inject FirebaseParser firebaseParser;
    @Inject RealmRepository realmRepository;

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.e(TAG, dataSnapshot.getKey() + " : added");
        copyToRealmOrUpdate(dataSnapshot);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.e(TAG, dataSnapshot.getKey() + " : changed");
        copyToRealmOrUpdate(dataSnapshot);
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.e(TAG, dataSnapshot.getKey() + " : removed");
        RealmObject object = getObject(dataSnapshot.getKey());
        object.removeFromRealm();
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

    private void copyToRealmOrUpdate(DataSnapshot dataSnapshot) {

        RealmObject object = parseObject(dataSnapshot);

        if (object != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
        }
    }

    public abstract RealmObject parseObject(DataSnapshot dataSnapshot);
    public abstract RealmObject getObject(String dataSnapshotKey);

}
