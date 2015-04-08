package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ConversationPartCursorParser;
import com.shinav.mathapp.db.pojo.ConversationPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.ConversationPart.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TABLE_NAME;

public class ConversationPartRepository {

    @Inject SqlBrite db;

    @Inject
    public ConversationPartRepository() {
    }

    public Observable<List<ConversationPart>> getByConversationKey(String conversationKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + CONVERSATION_KEY + " = ?"
                , conversationKey
        ).map(new ConversationPartCursorParser());
    }

}
