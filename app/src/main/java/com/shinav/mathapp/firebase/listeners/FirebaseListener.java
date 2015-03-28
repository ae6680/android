package com.shinav.mathapp.firebase.listeners;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class FirebaseListener implements ChildEventListener {

    public static final String TAG = "FirebaseListener";

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        copyToRealmOrUpdate(dataSnapshot);
        Log.e(TAG, dataSnapshot.getKey() + " : added");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        copyToRealmOrUpdate(dataSnapshot);
        Log.e(TAG, dataSnapshot.getKey() + " : changed");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        RealmObject object = getObject(dataSnapshot.getKey());
        object.removeFromRealm();
        Log.e(TAG, dataSnapshot.getKey() + " : removed");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

    private void copyToRealmOrUpdate(DataSnapshot dataSnapshot) {

        Realm realm = getRealm();

        RealmObject object = parseObject(dataSnapshot);

        if (object != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
        }
    }

    public abstract RealmObject parseObject(DataSnapshot dataSnapshot);
    public abstract RealmObject getObject(String dataSnapshotKey);
    public abstract Realm getRealm();

}
