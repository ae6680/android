package com.shinav.mathapp.conversation;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Conversation extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private RealmList<ConversationPart> conversationParts;

    public Conversation() { }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<ConversationPart> getConversationParts() {
        return conversationParts;
    }

    public void setConversationParts(RealmList<ConversationPart> conversationParts) {
        this.conversationParts = conversationParts;
    }
}
