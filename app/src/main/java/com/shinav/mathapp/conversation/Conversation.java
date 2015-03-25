package com.shinav.mathapp.conversation;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Conversation extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private RealmList<ConversationEntry> conversationEntries;

    public Conversation() { }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<ConversationEntry> getConversationEntries() {
        return conversationEntries;
    }

    public void setConversationEntries(RealmList<ConversationEntry> conversationEntries) {
        this.conversationEntries = conversationEntries;
    }
}
