package com.shinav.mathapp.approach;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Approach extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private RealmList<ApproachEntry> approachEntries;

    public Approach() { }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<ApproachEntry> getApproachEntries() {
        return approachEntries;
    }

    public void setApproachEntries(RealmList<ApproachEntry> approachEntries) {
        this.approachEntries = approachEntries;
    }
}
