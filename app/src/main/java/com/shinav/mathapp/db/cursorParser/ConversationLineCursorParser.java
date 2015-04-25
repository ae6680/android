package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.ConversationLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.ConversationLine.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.DELAY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.TYPING_DURATION;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.VALUE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationLineCursorParser implements Func1<Query, List<ConversationLine>> {

    @Inject
    public ConversationLineCursorParser() { }

    @Override public List<ConversationLine> call(Query query) {
        Cursor c = query.run();
        try {
            List<ConversationLine> conversationLines = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                conversationLines.add(fromCursor(c));
            }

            Collections.sort(conversationLines);
            return conversationLines;
        } finally {
            c.close();
        }
    }

    public ConversationLine fromCursor(Cursor c) {
        ConversationLine conversationLine = new ConversationLine();

        conversationLine.setKey(c.getString(c.getColumnIndex(KEY)));
        conversationLine.setConversationKey(c.getString(c.getColumnIndex(CONVERSATION_KEY)));
        conversationLine.setValue(c.getString(c.getColumnIndex(VALUE)));
        conversationLine.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        conversationLine.setDelay(c.getInt(c.getColumnIndex(DELAY)));
        conversationLine.setTypingDuration(c.getInt(c.getColumnIndex(TYPING_DURATION)));
        conversationLine.setAlignment(c.getInt(c.getColumnIndex(ALIGNMENT)));
        conversationLine.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));

        return conversationLine;
    }

}
