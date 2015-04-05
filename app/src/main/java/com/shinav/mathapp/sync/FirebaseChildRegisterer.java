package com.shinav.mathapp.sync;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.listener.FirebaseApproachListener;
import com.shinav.mathapp.firebase.listener.FirebaseApproachPartListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryListener;

import javax.inject.Inject;

import static com.shinav.mathapp.firebase.FirebaseInterface.Nodes;

public class FirebaseChildRegisterer {

    private final Firebase firebase;
    private final FirebaseQuestionListener firebaseQuestionListener;
    private final FirebaseStoryListener firebaseStoryListener;
    private final FirebaseConversationListener firebaseConversationListener;
    private final FirebaseApproachListener firebaseApproachListener;
    private final FirebaseApproachPartListener firebaseApproachPartListener;

    @Inject
    public FirebaseChildRegisterer(
            Firebase firebase,
            FirebaseQuestionListener firebaseQuestionListener,
            FirebaseStoryListener firebaseStoryListener,
            FirebaseConversationListener firebaseConversationListener,
            FirebaseApproachListener firebaseApproachListener,
            FirebaseApproachPartListener firebaseApproachPartListener
    ) {
        this.firebase = firebase;
        this.firebaseQuestionListener = firebaseQuestionListener;
        this.firebaseStoryListener = firebaseStoryListener;
        this.firebaseConversationListener = firebaseConversationListener;
        this.firebaseApproachListener = firebaseApproachListener;
        this.firebaseApproachPartListener = firebaseApproachPartListener;
    }

    public void register() {
        addChildEventListener(Nodes.QUESTIONS, firebaseQuestionListener);
        addChildEventListener(Nodes.STORIES, firebaseStoryListener);
        addChildEventListener(Nodes.CONVERSATIONS, firebaseConversationListener);
        addChildEventListener(Nodes.APPROACHES, firebaseApproachListener);
        addChildEventListener(Nodes.APPROACH_PARTS, firebaseApproachPartListener);
    }

    private void addChildEventListener(String node, ChildEventListener listener) {
        firebase.child(node).addChildEventListener(listener);
    }

}
