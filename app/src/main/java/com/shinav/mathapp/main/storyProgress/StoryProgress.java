package com.shinav.mathapp.main.storyProgress;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class StoryProgress extends RealmObject {

    private RealmList<StoryProgressPart> storyProgressParts;

    public StoryProgress() { }

    public RealmList<StoryProgressPart> getStoryProgressParts() {
        return storyProgressParts;
    }

    public void setStoryProgressParts(RealmList<StoryProgressPart> storyProgressParts) {
        this.storyProgressParts = storyProgressParts;
    }
}
