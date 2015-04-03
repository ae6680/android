package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.model.Question;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

public class FirebaseQuestionListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final SqlBrite db;

    @Inject
    public FirebaseQuestionListener(
            FirebaseParser firebaseParser,
            SqlBrite db
    ) {
        this.firebaseParser = firebaseParser;
        this.db = db;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Question question = firebaseParser.parseQuestion(dataSnapshot);
        db.insert(Tables.Question.TABLE_NAME, question.getContentValues());
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(FirebaseError firebaseError) {

    }

}
