package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.QuestionExplanation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation.TEXT;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionExplanationCursorParser implements Func1<Query, List<QuestionExplanation>> {

    @Inject
    public QuestionExplanationCursorParser() { }

    @Override public List<QuestionExplanation> call(Query query) {
        Cursor c = query.run();
        try {
            List<QuestionExplanation> questionExplanations = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                questionExplanations.add(fromCursor(c));
            }
            return questionExplanations;
        } finally {
            c.close();
        }
    }

    private QuestionExplanation fromCursor(Cursor c) {
        QuestionExplanation questionExplanation = new QuestionExplanation();

        questionExplanation.setKey(c.getString(c.getColumnIndex(KEY)));
        questionExplanation.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));
        questionExplanation.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));
        questionExplanation.setText(c.getString(c.getColumnIndex(TEXT)));
        questionExplanation.setPosition(c.getInt(c.getColumnIndex(POSITION)));

        return questionExplanation;
    }

}
