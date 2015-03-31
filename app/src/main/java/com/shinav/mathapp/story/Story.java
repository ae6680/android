package com.shinav.mathapp.story;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Story extends RealmObject {

    @PrimaryKey
    private String firebaseKey;
    private RealmList<StoryPart> storyParts;

    public Story() { }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<StoryPart> getStoryParts() {
        return storyParts;
    }

    public void setStoryParts(RealmList<StoryPart> storyParts) {
        this.storyParts = storyParts;
    }

    public ArrayList<StoryPart> filterOnQuestionType() {
        ArrayList<StoryPart> questionStories = new ArrayList<>();

        for (StoryPart storyPart : storyParts) {
            if (storyPart.isQuestion()) {
                questionStories.add(storyPart);
            }
        }

        return questionStories;
    }

}
