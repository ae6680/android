package com.shinav.mathapp.repository;

import com.shinav.mathapp.conversation.Conversation;
import com.shinav.mathapp.main.storyProgress.StoryProgress;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.story.Story;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class RealmRepository {

    private final Realm realm;

    @Inject
    public RealmRepository(Realm realm) {
        this.realm = realm;
    }

    public List<Question> getQuestions() {
        return realm.where(Question.class).findAll();
    }

    public Question getQuestion(String firebaseKey) {
        return realm.where(Question.class).contains("firebaseKey", firebaseKey).findFirst();
    }

    public Story getStory(String firebaseKey) {
        return realm.where(Story.class).contains("firebaseKey", firebaseKey).findFirst();
    }

    public Conversation getConversation(String firebaseKey) {
        return realm.where(Conversation.class).contains("firebaseKey", firebaseKey).findFirst();
    }

    public StoryProgress getStoryProgress() {
        return realm.where(StoryProgress.class).findFirst();
    }
}
