package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.QuestionExplanation;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TEXT;

public class QuestionExplanationFirebaseMapper extends FirebaseMapper<QuestionExplanation> {

    @Inject public QuestionExplanationFirebaseMapper() { }

    @Override public QuestionExplanation fromDataSnapshot(DataSnapshot dataSnapshot) {
        QuestionExplanation questionExplanation = new QuestionExplanation();

        String questionKey =    getString(dataSnapshot, QUESTION_KEY);
        String text =           getString(dataSnapshot, TEXT);
        String imageUrl =       getString(dataSnapshot, IMAGE_URL);
        String position =       getString(dataSnapshot, POSITION);

        questionExplanation.setKey(dataSnapshot.getKey());
        questionExplanation.setQuestionKey(questionKey);
        questionExplanation.setText(text);
        questionExplanation.setImageUrl(imageUrl);
        questionExplanation.setPosition(Integer.parseInt(position));

        return questionExplanation;
    }

}
