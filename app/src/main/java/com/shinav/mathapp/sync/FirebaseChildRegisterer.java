package com.shinav.mathapp.sync;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.listeners.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listeners.FirebaseQuestionListener;
import com.shinav.mathapp.firebase.listeners.FirebaseStoryListener;

import javax.inject.Inject;

import static com.shinav.mathapp.firebase.FirebaseInterface.Nodes;

public class FirebaseChildRegisterer {

    private final Firebase firebase;
    private final FirebaseQuestionListener firebaseQuestionListener;
    private final FirebaseStoryListener firebaseStoryListener;
    private final FirebaseConversationListener firebaseConversationListener;

    @Inject
    public FirebaseChildRegisterer(
            Firebase firebase,
            FirebaseQuestionListener firebaseQuestionListener,
            FirebaseStoryListener firebaseStoryListener,
            FirebaseConversationListener firebaseConversationListener
    ) {
        this.firebase = firebase;
        this.firebaseQuestionListener = firebaseQuestionListener;
        this.firebaseStoryListener = firebaseStoryListener;
        this.firebaseConversationListener = firebaseConversationListener;
    }

    public void register() {
        addChildEventListener(Nodes.QUESTIONS, firebaseQuestionListener);
        addChildEventListener(Nodes.STORIES, firebaseStoryListener);
        addChildEventListener(Nodes.CONVERSATIONS, firebaseConversationListener);
    }

    private void addChildEventListener(String node, ChildEventListener listener) {
        firebase.child(node).addChildEventListener(listener);
    }

}
