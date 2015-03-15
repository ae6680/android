package com.shinav.mathapp.firebase;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.question.Question;

import io.realm.RealmList;

public class FirebaseParser {

    private static final String TAG = "FirebaseParser";

    public static FirebaseParser getInstance() {
        return new FirebaseParser();
    }

    public Question parseQuestion(DataSnapshot dataSnapshot) {
        Question question = new Question();

        try {
            String answer = getStringAttribute(dataSnapshot, FirebaseInterface.Question.ANSWER);
            String value = getStringAttribute(dataSnapshot, FirebaseInterface.Question.VALUE);
            String title = getStringAttribute(dataSnapshot, FirebaseInterface.Question.TITLE);
            DataSnapshot approachesSnapshot = dataSnapshot.child(FirebaseInterface.Question.APPROACHES);

            question.setFirebaseKey(dataSnapshot.getKey());
            question.setAnswer(answer);
            question.setValue(value);
            question.setTitle(title);

            RealmList<Approach> approaches = new RealmList<>();
            for (int i = 0; i < approachesSnapshot.getChildrenCount(); i++) {
                approaches.add(parseApproach(approachesSnapshot.child("approach-"+i)));
            }

            question.setApproaches(approaches);

            return question;

        } catch (NullPointerException e) {
            Log.e(TAG, "Field or value not set");
            System.out.println(dataSnapshot.getKey());
            System.out.println(dataSnapshot.getValue());
            e.printStackTrace();

            return null;
        }
    }

    private Approach parseApproach(DataSnapshot dataSnapshot) {

        Approach approach = new Approach();

        String positionAttribute = getStringAttribute(dataSnapshot, FirebaseInterface.Approaches.POSITION);
        String valueAttribute = getStringAttribute(dataSnapshot, FirebaseInterface.Approaches.VALUE);

        approach.setPosition(Integer.parseInt(positionAttribute));
        approach.setText(valueAttribute);

        return approach;
    }

    private String getStringAttribute(DataSnapshot dataSnapshot, String attribute) {
        return dataSnapshot.child(attribute).getValue().toString();
    }

}
