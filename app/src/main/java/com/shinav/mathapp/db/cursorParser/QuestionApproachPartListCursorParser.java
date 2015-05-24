package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class QuestionApproachPartListCursorParser implements Func1<Query, List<QuestionApproachPart>> {

    @Inject
    public QuestionApproachPartListCursorParser() { }

    @Override public List<QuestionApproachPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<QuestionApproachPart> questionApproachParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                questionApproachParts.add(fromCursor(c));
            }

            Collections.sort(questionApproachParts);

            return questionApproachParts;
        } finally {
            c.close();
        }
    }

    public QuestionApproachPart fromCursor(Cursor c) {
        QuestionApproachPart questionApproachPart = new QuestionApproachPart();

        questionApproachPart.setKey(c.getString(c.getColumnIndex(KEY)));
        questionApproachPart.setApproachKey(c.getString(c.getColumnIndex(QUESTION_APPROACH_KEY)));
        questionApproachPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        questionApproachPart.setValue(c.getString(c.getColumnIndex(VALUE)));

        return questionApproachPart;
    }

}
