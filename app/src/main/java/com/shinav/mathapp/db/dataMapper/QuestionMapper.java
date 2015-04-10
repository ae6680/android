package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Question;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Question.ANSWER;
import static com.shinav.mathapp.db.helper.Tables.Question.EXPLANATION;
import static com.shinav.mathapp.db.helper.Tables.Question.KEY;
import static com.shinav.mathapp.db.helper.Tables.Question.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Question.TITLE;
import static com.shinav.mathapp.db.helper.Tables.Question.VALUE;

public class QuestionMapper {

    @Inject SqlBrite db;

    @Inject
    public QuestionMapper() { }

    private ContentValues getContentValues(Question question) {
        ContentValues values = new ContentValues();

        values.put(KEY, question.getKey());
        values.put(VALUE, question.getValue());
        values.put(TITLE, question.getTitle());
        values.put(ANSWER, question.getAnswer());
        values.put(EXPLANATION, question.getExplanation());

        return values;
    }

    public void insert(Question question) {
        db.insert(TABLE_NAME, getContentValues(question));
    }

}
