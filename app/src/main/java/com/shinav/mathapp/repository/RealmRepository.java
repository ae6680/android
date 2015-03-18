package com.shinav.mathapp.repository;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.conversation.Conversation;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.story.Story;

import java.util.List;

import io.realm.Realm;

public class RealmRepository {

    private static RealmRepository REPOSITORY = new RealmRepository();
    private final Realm REALM = Realm.getInstance(MyApplication.getAppContext());

    public static RealmRepository getInstance() {
        return REPOSITORY;
    }

    public List<Question> getQuestions() {
        return REALM.where(Question.class).findAll();
    }

    public Question getQuestion(String firebaseKey) {
        return REALM.where(Question.class).contains("firebaseKey", firebaseKey).findFirst();
    }

    public Story getStory(String firebaseKey) {
        return REALM.where(Story.class).contains("firebaseKey", firebaseKey).findFirst();
    }

    public Conversation getConversation(String firebaseKey) {
        return REALM.where(Conversation.class).contains("firebaseKey", firebaseKey).findFirst();
    }
}
