package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.TutorialPart;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.TutorialPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TUTORIAL_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialPart.TYPE_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class TutorialPartCursorParser implements Func1<Query, List<TutorialPart>> {

    @Override public List<TutorialPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<TutorialPart> tutorialParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                tutorialParts.add(fromCursor(c));
            }
            return tutorialParts;
        } finally {
            c.close();
        }
    }

    public TutorialPart fromCursor(Cursor c) {
        TutorialPart tutorialPart = new TutorialPart();

        tutorialPart.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorialPart.setTutorialKey(c.getString(c.getColumnIndex(TUTORIAL_KEY)));
        tutorialPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        tutorialPart.setType(c.getString(c.getColumnIndex(TYPE)));
        tutorialPart.setTypeKey(c.getString(c.getColumnIndex(TYPE_KEY)));

        return tutorialPart;
    }

}
