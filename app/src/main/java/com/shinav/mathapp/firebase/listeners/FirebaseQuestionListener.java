package com.shinav.mathapp.firebase.listeners;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.repository.RealmRepository;

import io.realm.Realm;

public class FirebaseQuestionListener implements ChildEventListener {

    public static final String TAG = "FirebaseListener";
    public static final String CLASS = "Question";
    private final Realm REALM = Realm.getInstance(MyApplication.getAppContext());

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.e(TAG, CLASS + " : added");
        copyToRealmOrUpdate(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.e(TAG, CLASS + " : changed");
        copyToRealmOrUpdate(dataSnapshot);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.e(TAG, CLASS + " : removed");
        Question question = RealmRepository.getInstance().getQuestion(dataSnapshot.getKey());
        question.removeFromRealm();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override
    public void onCancelled(FirebaseError firebaseError) {  }

    public void copyToRealmOrUpdate(DataSnapshot dataSnapshot) {
        Question question = FirebaseParser.parseQuestion(dataSnapshot);

        if (question != null) {
            REALM.beginTransaction();
            REALM.copyToRealmOrUpdate(question);
            REALM.commitTransaction();
        }
    }
}
