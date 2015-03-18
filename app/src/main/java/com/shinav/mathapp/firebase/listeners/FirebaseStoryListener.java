package com.shinav.mathapp.firebase.listeners;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.story.Story;

import io.realm.Realm;

public class FirebaseStoryListener implements ChildEventListener {

    public static final String TAG = "FirebaseListener";
    public static final String CLASS = "Story";
    private final Realm REALM = Realm.getInstance(MyApplication.getAppContext());

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.e(TAG, CLASS + " : added");
        copyToRealmOrUpdate(dataSnapshot);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.e(TAG, CLASS + " : changed");
        copyToRealmOrUpdate(dataSnapshot);
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.e(TAG, CLASS + " : removed");
        Story story = RealmRepository.getInstance().getStory(dataSnapshot.getKey());
        story.removeFromRealm();
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

    public void copyToRealmOrUpdate(DataSnapshot dataSnapshot) {
        Story story = FirebaseParser.getInstance().parseStory(dataSnapshot);

        if (story != null) {
            REALM.beginTransaction();
            REALM.copyToRealmOrUpdate(story);
            REALM.commitTransaction();
        }
    }

}
