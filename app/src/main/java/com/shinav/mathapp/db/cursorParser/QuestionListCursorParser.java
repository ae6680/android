package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Question;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionListCursorParser implements Func1<Query, List<Question>> {

    @Inject
    public QuestionListCursorParser() { }

    @Override public List<Question> call(Query query) {
        Cursor c = query.run();
        try {
            List<Question> storyProgressParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyProgressParts.add(fromCursor(c));
            }
            return storyProgressParts;
        } finally {
            c.close();
        }
    }

    public Question fromCursor(Cursor c) {
        Question question = new Question();

        question.setKey(c.getString(c.getColumnIndex(KEY)));
        question.setTitle(c.getString(c.getColumnIndex(TITLE)));
        question.setAnswer(c.getString(c.getColumnIndex(ANSWER)));

        return question;
    }

}
