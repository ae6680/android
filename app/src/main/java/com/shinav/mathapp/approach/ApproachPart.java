package com.shinav.mathapp.approach;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class ApproachPart extends RealmObject {

    private String text;
    private int position;

    public ApproachPart() { }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
