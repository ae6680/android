package com.shinav.mathapp.approach;

public class Approach {

    private String text;
    private int position;

    public Approach(String text, int position) {
        this.text = text;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

}
