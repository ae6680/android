package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Question;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.shinav.mathapp.db.helper.Tables.Question.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionMapper implements rx.functions.Func1<Query, Question> {

    @Inject SqlBrite db;

    @Inject
    public QuestionMapper() { }

    @Override public Question call(Query query) {
        Cursor c = query.run();
        try {
            if (!c.moveToFirst()) {
                return null;
            }

            return fromCursor(c);
        } finally {
            c.close();
        }
    }

    public Question fromCursor(Cursor c) {
        Question question = new Question();

        question.setKey(c.getString(c.getColumnIndex(KEY)));
        question.setValue(c.getString(c.getColumnIndex(VALUE)));
        question.setTitle(c.getString(c.getColumnIndex(TITLE)));
        question.setAnswer(c.getString(c.getColumnIndex(ANSWER)));

        return question;
    }

    public ContentValues getContentValues(Question question) {
        ContentValues values = new ContentValues();

        values.put(KEY, question.getKey());
        values.put(VALUE, question.getValue());
        values.put(TITLE, question.getTitle());
        values.put(ANSWER, question.getAnswer());

        return values;
    }

    public Subscription getByKey(String questionKey, Action1<Question> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , questionKey
        )
                .map(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    public void insert(Question question) {
        db.insert(TABLE_NAME, getContentValues(question));
    }
}
