package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.QuestionExplanation;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TEXT;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionExplanationListMapper extends ListMapper<QuestionExplanation> {

    @Inject
    public QuestionExplanationListMapper() { }

    @Override public List<QuestionExplanation> call(Query query) {
        List<QuestionExplanation> questionExplanations = super.call(query);
        Collections.sort(questionExplanations);

        return questionExplanations;
    }

    @Override public QuestionExplanation fromCursor(Cursor c) {
        QuestionExplanation questionExplanation = new QuestionExplanation();

        questionExplanation.setKey(c.getString(c.getColumnIndex(KEY)));
        questionExplanation.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));
        questionExplanation.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));
        questionExplanation.setText(c.getString(c.getColumnIndex(TEXT)));
        questionExplanation.setPosition(c.getInt(c.getColumnIndex(POSITION)));

        return questionExplanation;
    }

}
