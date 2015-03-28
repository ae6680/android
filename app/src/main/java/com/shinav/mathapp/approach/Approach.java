package com.shinav.mathapp.approach;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Approach extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private RealmList<ApproachPart> approachParts;

    public Approach() { }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<ApproachPart> getApproachParts() {
        return approachParts;
    }

    public void setApproachParts(RealmList<ApproachPart> approachParts) {
        this.approachParts = approachParts;
    }
}
