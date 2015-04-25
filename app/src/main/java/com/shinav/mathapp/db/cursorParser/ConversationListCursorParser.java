package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Conversation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationListCursorParser implements Func1<Query, List<Conversation>> {

    @Inject ConversationCursorParser parser;

    @Inject
    public ConversationListCursorParser() { }

    @Override public List<Conversation> call(Query query) {
        Cursor c = query.run();
        try {
            List<Conversation> conversations = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                conversations.add(fromCursor(c));
            }
            return conversations;
        } finally {
            c.close();
        }
    }

    private Conversation fromCursor(Cursor c) {
        return parser.fromCursor(c);
    }

}
