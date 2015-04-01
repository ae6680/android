package com.shinav.mathapp.main.storyProgress;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class StoryProgress extends RealmObject {

    @PrimaryKey
    private String identifier;

    private RealmList<StoryProgressPart> storyProgressParts;

    public StoryProgress() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public RealmList<StoryProgressPart> getStoryProgressParts() {
        return storyProgressParts;
    }

    public void setStoryProgressParts(RealmList<StoryProgressPart> storyProgressParts) {
        this.storyProgressParts = storyProgressParts;
    }
}
