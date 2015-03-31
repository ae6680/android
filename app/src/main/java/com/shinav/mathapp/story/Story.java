package com.shinav.mathapp.story;

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

}
