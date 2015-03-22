package com.shinav.mathapp.question;

import com.shinav.mathapp.approach.Approach;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Question extends RealmObject {

    @PrimaryKey
    private String firebaseKey;

    private String value;
    private String title;
    private String answer;
    private RealmList<Approach> approach;

    public Question() { }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public RealmList<Approach> getApproach() {
        return approach;
    }

    public void setApproach(RealmList<Approach> approach) {
        this.approach = approach;
    }

}
