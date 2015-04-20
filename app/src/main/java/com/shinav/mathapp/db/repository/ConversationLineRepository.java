package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ConversationLineCursorParser;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.ConversationLine.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.TABLE_NAME;

public class ConversationLineRepository {

    @Inject SqlBrite db;
    @Inject ConversationLineCursorParser parser;

    @Inject
    public ConversationLineRepository() { }

    public Observable<List<ConversationLine>> getByConversationKey(String conversationKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + CONVERSATION_KEY + " = ?"
                , conversationKey
        ).map(parser);
    }

}
