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
    private RealmList<StoryEntry> storyEntries;

    public Story() { }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<StoryEntry> getStoryEntries() {
        return storyEntries;
    }

    public void setStoryEntries(RealmList<StoryEntry> storyEntries) {
        this.storyEntries = storyEntries;
    }

    public ArrayList<StoryEntry> filterOnQuestionType() {
        ArrayList<StoryEntry> questionStories = new ArrayList<>();

        for (StoryEntry storyEntry : storyEntries) {
            if (storyEntry.isQuestion()) {
                questionStories.add(storyEntry);
            }
        }

        return questionStories;
    }

}
