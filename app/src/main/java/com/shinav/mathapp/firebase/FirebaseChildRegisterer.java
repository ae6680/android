package com.shinav.mathapp.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.listener.FirebaseApproachListener;
import com.shinav.mathapp.firebase.listener.FirebaseApproachPartListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationLineListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryboardFrameListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryboardListener;
import com.shinav.mathapp.firebase.listener.FirebaseTutorialFrameListener;
import com.shinav.mathapp.firebase.listener.FirebaseTutorialListener;

import javax.inject.Inject;

import static com.shinav.mathapp.firebase.FirebaseNodes.Nodes;

public class FirebaseChildRegisterer {

    private final Firebase firebase;
    private final FirebaseQuestionListener firebaseQuestionListener;
    private final FirebaseStoryboardListener firebaseStoryboardListener;
    private final FirebaseConversationListener firebaseConversationListener;
    private final FirebaseApproachListener firebaseApproachListener;
    private final FirebaseApproachPartListener firebaseApproachPartListener;
    private final FirebaseStoryboardFrameListener firebaseStoryboardFrameListener;
    private final FirebaseConversationLineListener firebaseConversationLineListener;
    private final FirebaseTutorialListener firebaseTutorialListener;
    private final FirebaseTutorialFrameListener firebaseTutorialFrameListener;

    @Inject
    public FirebaseChildRegisterer(
            Firebase firebase,
            FirebaseQuestionListener firebaseQuestionListener,
            FirebaseStoryboardListener firebaseStoryboardListener,
            FirebaseConversationListener firebaseConversationListener,
            FirebaseConversationLineListener firebaseConversationLineListener,
            FirebaseApproachListener firebaseApproachListener,
            FirebaseApproachPartListener firebaseApproachPartListener,
            FirebaseStoryboardFrameListener firebaseStoryboardFrameListener,
            FirebaseTutorialListener firebaseTutorialListener,
            FirebaseTutorialFrameListener firebaseTutorialFrameListener
    ) {
        this.firebase = firebase;
        this.firebaseQuestionListener = firebaseQuestionListener;
        this.firebaseStoryboardListener = firebaseStoryboardListener;
        this.firebaseConversationListener = firebaseConversationListener;
        this.firebaseConversationLineListener = firebaseConversationLineListener;
        this.firebaseApproachListener = firebaseApproachListener;
        this.firebaseApproachPartListener = firebaseApproachPartListener;
        this.firebaseStoryboardFrameListener = firebaseStoryboardFrameListener;
        this.firebaseTutorialListener = firebaseTutorialListener;
        this.firebaseTutorialFrameListener = firebaseTutorialFrameListener;
    }

    public void register() {
        addChildEventListener(Nodes.QUESTIONS, firebaseQuestionListener);
        addChildEventListener(Nodes.APPROACHES, firebaseApproachListener);
        addChildEventListener(Nodes.APPROACH_PARTS, firebaseApproachPartListener);
        addChildEventListener(Nodes.STORYBOARD, firebaseStoryboardListener);
        addChildEventListener(Nodes.STORYBOARD_FRAMES, firebaseStoryboardFrameListener);
        addChildEventListener(Nodes.CONVERSATIONS, firebaseConversationListener);
        addChildEventListener(Nodes.CONVERSATION_LINES, firebaseConversationLineListener);
        addChildEventListener(Nodes.TUTORIALS, firebaseTutorialListener);
        addChildEventListener(Nodes.TUTORIAL_FRAMES, firebaseTutorialFrameListener);
    }

    private void addChildEventListener(String node, ChildEventListener listener) {
        firebase.child(node).addChildEventListener(listener);
    }

}
