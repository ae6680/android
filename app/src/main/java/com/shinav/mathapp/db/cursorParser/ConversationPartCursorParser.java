package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.ConversationPart;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.ConversationPart.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.DELAY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.MESSAGE;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TYPING_DURATION;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationPartCursorParser implements Func1<Query, List<ConversationPart>> {

    @Inject
    public ConversationPartCursorParser() { }

    @Override public List<ConversationPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<ConversationPart> conversationParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                conversationParts.add(fromCursor(c));
            }
            return conversationParts;
        } finally {
            c.close();
        }
    }

    public ConversationPart fromCursor(Cursor c) {
        ConversationPart conversationPart = new ConversationPart();

        conversationPart.setKey(c.getString(c.getColumnIndex(KEY)));
        conversationPart.setConversationKey(c.getString(c.getColumnIndex(CONVERSATION_KEY)));
        conversationPart.setMessage(c.getString(c.getColumnIndex(MESSAGE)));
        conversationPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        conversationPart.setDelay(c.getInt(c.getColumnIndex(DELAY)));
        conversationPart.setTypingDuration(c.getInt(c.getColumnIndex(TYPING_DURATION)));
        conversationPart.setAlignment(c.getInt(c.getColumnIndex(ALIGNMENT)));

        return conversationPart;
    }

}
