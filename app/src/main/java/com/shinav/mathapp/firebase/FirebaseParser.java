package com.shinav.mathapp.firebase;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.question.Question;

import java.util.Map;

public class FirebaseParser {

    private static final String TAG = "FirebaseParser";

    public static Question parseQuestion(DataSnapshot dataSnapshot) {
        Question question = new Question();

        Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();

        try {
            String answer = data.get(FirebaseInterface.Question.ANSWER).toString();
            String value = data.get(FirebaseInterface.Question.VALUE).toString();
            String title = data.get(FirebaseInterface.Question.TITLE).toString();
            boolean calculatorAllowed = (boolean) data.get(FirebaseInterface.Question.CALCULATOR_ALLOWED);

            question.setFirebaseKey(dataSnapshot.getKey());
            question.setAnswer(answer);
            question.setValue(value);
            question.setTitle(title);
            question.setCalculatorAllowed(calculatorAllowed);

            return question;

        } catch (NullPointerException e) {
            Log.e(TAG, "Field or value not set");
            System.out.println(dataSnapshot.getKey());
            System.out.println(dataSnapshot.getValue());
            e.printStackTrace();

            return null;
        }
    }
}
