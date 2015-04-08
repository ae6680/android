package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ConversationCursorParser;
import com.shinav.mathapp.db.pojo.Conversation;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Conversation.KEY;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TABLE_NAME;

public class ConversationRepository {

    @Inject SqlBrite db;

    @Inject
    public ConversationRepository() { }

    public Observable<Conversation> getByKey(String conversationKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , conversationKey
        ).map(new ConversationCursorParser());
    }

}
