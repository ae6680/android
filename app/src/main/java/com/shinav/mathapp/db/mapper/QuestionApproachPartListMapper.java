package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionApproachPartListMapper extends ListMapper<QuestionApproachPart> {

    @Inject
    public QuestionApproachPartListMapper() { }

    @Override public List<QuestionApproachPart> call(Query query) {
        List<QuestionApproachPart> questionApproachParts = super.call(query);
        Collections.sort(questionApproachParts);

        return questionApproachParts;
    }

    @Override public QuestionApproachPart fromCursor(Cursor c) {
        QuestionApproachPart questionApproachPart = new QuestionApproachPart();

        questionApproachPart.setKey(c.getString(c.getColumnIndex(KEY)));
        questionApproachPart.setApproachKey(c.getString(c.getColumnIndex(QUESTION_APPROACH_KEY)));
        questionApproachPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        questionApproachPart.setValue(c.getString(c.getColumnIndex(VALUE)));

        return questionApproachPart;
    }

}
