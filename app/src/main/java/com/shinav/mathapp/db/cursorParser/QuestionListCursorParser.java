package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Question;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.Question.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.PROGRESS_STATE;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionListCursorParser implements Func1<Query, List<Question>> {

    @Inject
    public QuestionListCursorParser() { }

    @Override public List<Question> call(Query query) {
        Cursor c = query.run();
        try {
            List<Question> questions = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                questions.add(fromCursor(c));
            }
            return questions;
        } finally {
            c.close();
        }
    }

    public Question fromCursor(Cursor c) {
        Question question = new Question();

        question.setKey(c.getString(c.getColumnIndex(KEY)));
        question.setTitle(c.getString(c.getColumnIndex(TITLE)));
        question.setBackgroundImageUrl(c.getString(c.getColumnIndex(BACKGROUND_IMAGE_URL)));
        question.setProgressState(c.getInt(c.getColumnIndex(PROGRESS_STATE)));

        return question;
    }

}
