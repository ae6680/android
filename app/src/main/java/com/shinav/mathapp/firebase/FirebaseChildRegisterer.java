package com.shinav.mathapp.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.listener.FirebaseConversationLineListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionApproachListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionApproachPartListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryboardFrameListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryboardListener;
import com.shinav.mathapp.firebase.listener.FirebaseTutorialFrameListener;
import com.shinav.mathapp.firebase.listener.FirebaseTutorialListener;

import javax.inject.Inject;

import static com.shinav.mathapp.firebase.FirebaseNodes.Nodes;

public class FirebaseChildRegisterer {

    @Inject Firebase firebase;

    @Inject FirebaseTutorialListener firebaseTutorialListener;
    @Inject FirebaseTutorialFrameListener firebaseTutorialFrameListener;

    @Inject FirebaseStoryboardListener firebaseStoryboardListener;
    @Inject FirebaseStoryboardFrameListener firebaseStoryboardFrameListener;

    @Inject FirebaseConversationListener firebaseConversationListener;
    @Inject FirebaseConversationLineListener firebaseConversationLineListener;

    @Inject FirebaseQuestionListener firebaseQuestionListener;
    @Inject FirebaseQuestionApproachListener firebaseQuestionApproachListener;
    @Inject FirebaseQuestionApproachPartListener firebaseQuestionApproachPartListener;

    @Inject
    public FirebaseChildRegisterer() { }

    public void register() {
        addChildEventListener(Nodes.TUTORIALS, firebaseTutorialListener);
        addChildEventListener(Nodes.TUTORIAL_FRAMES, firebaseTutorialFrameListener);

        addChildEventListener(Nodes.STORYBOARD, firebaseStoryboardListener);
        addChildEventListener(Nodes.STORYBOARD_FRAMES, firebaseStoryboardFrameListener);

        addChildEventListener(Nodes.CONVERSATIONS, firebaseConversationListener);
        addChildEventListener(Nodes.CONVERSATION_LINES, firebaseConversationLineListener);

        addChildEventListener(Nodes.QUESTIONS, firebaseQuestionListener);
        addChildEventListener(Nodes.APPROACHES, firebaseQuestionApproachListener);
        addChildEventListener(Nodes.APPROACH_PARTS, firebaseQuestionApproachPartListener);
    }

    private void addChildEventListener(String node, ChildEventListener listener) {
        firebase.child(node).addChildEventListener(listener);
    }

}
