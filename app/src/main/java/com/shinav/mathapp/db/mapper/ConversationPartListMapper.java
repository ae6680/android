package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.conversation.ConversationPart;

import java.util.ArrayList;
import java.util.List;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationPartListMapper implements rx.functions.Func1<Query, List<ConversationPart>> {

    @Override public List<ConversationPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<ConversationPart> conversationParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                conversationParts.add(ConversationPart.fromCursor(c));
            }
            return conversationParts;
        } finally {
            c.close();
        }
    }
}
