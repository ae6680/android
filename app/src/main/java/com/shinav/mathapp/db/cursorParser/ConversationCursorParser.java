package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Conversation;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.Conversation.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Conversation.KEY;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationCursorParser implements Func1<Query, Conversation> {

    @Inject
    public ConversationCursorParser() { }

    @Override public Conversation call(Query query) {
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

    public Conversation fromCursor(Cursor c) {
        Conversation conversation = new Conversation();

        conversation.setKey(c.getString(c.getColumnIndex(KEY)));
        conversation.setTitle(c.getString(c.getColumnIndex(TITLE)));
        conversation.setBackgroundImageUrl(c.getString(c.getColumnIndex(BACKGROUND_IMAGE_URL)));

        return conversation;
    }

}
