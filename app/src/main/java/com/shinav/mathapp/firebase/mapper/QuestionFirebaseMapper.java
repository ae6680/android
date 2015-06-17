package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.Question;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Question.ANNEX_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.shinav.mathapp.db.helper.Tables.Question.VALUE;

public class QuestionFirebaseMapper extends FirebaseMapper<Question> {

    @Inject public QuestionFirebaseMapper() { }

    @Override public Question fromDataSnapshot(DataSnapshot dataSnapshot) {
        Question question = new Question();

        String answer =             getString(dataSnapshot, ANSWER);
        String value =              getString(dataSnapshot, VALUE);
        String title =              getString(dataSnapshot, TITLE);
        String annexImageUrl =      getString(dataSnapshot, ANNEX_IMAGE_URL);
        String backgroundImageUrl = getString(dataSnapshot, BACKGROUND_IMAGE_URL);

        question.setKey(dataSnapshot.getKey());
        question.setAnswer(answer);
        question.setValue(value);
        question.setTitle(title);
        question.setAnnexImageUrl(annexImageUrl);
        question.setBackgroundImageUrl(backgroundImageUrl);

        return question;
    }

}
