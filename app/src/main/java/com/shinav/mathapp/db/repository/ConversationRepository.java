package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.ConversationCursorParser;
import com.shinav.mathapp.db.cursorParser.ConversationListCursorParser;
import com.shinav.mathapp.db.pojo.Conversation;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.Conversation.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Conversation.KEY;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TITLE;

public class ConversationRepository {

    @Inject SqlBrite db;
    @Inject ConversationCursorParser parser;
    @Inject ConversationListCursorParser listParser;

    @Inject
    public ConversationRepository() { }

    public Observable<Conversation> getByKey(String conversationKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , conversationKey
        ).map(parser);
    }

    public Observable<List<Conversation>> getCollection(String conversationKeys) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT " +
                        KEY + ", " +
                        TITLE + ", " +
                        BACKGROUND_IMAGE_URL +
                        " FROM " + TABLE_NAME +
                        " WHERE " + KEY + " IN ('" + conversationKeys + "')"
        ).map(listParser).first();
    }

}
