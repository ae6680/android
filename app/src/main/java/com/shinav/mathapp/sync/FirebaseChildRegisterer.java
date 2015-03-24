package com.shinav.mathapp.sync;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.listeners.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listeners.FirebaseQuestionListener;
import com.shinav.mathapp.firebase.listeners.FirebaseStoryListener;

import javax.inject.Inject;

import static com.shinav.mathapp.firebase.FirebaseInterface.Nodes;

public final class FirebaseChildRegisterer {

    private final Firebase firebase;

    @Inject
    public FirebaseChildRegisterer(Firebase firebase) {
        this.firebase = firebase;
    }

    public void register() {
        addChildEventListener(Nodes.QUESTIONS, new FirebaseQuestionListener());
        addChildEventListener(Nodes.STORIES, new FirebaseStoryListener());
        addChildEventListener(Nodes.CONVERSATIONS, new FirebaseConversationListener());
    }

    private void addChildEventListener(String node, ChildEventListener listener) {
        firebase.child(node).addChildEventListener(listener);
    }

}
