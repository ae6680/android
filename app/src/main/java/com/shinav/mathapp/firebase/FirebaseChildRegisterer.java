package com.shinav.mathapp.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.listener.FirebaseCutsceneLineListener;
import com.shinav.mathapp.firebase.listener.FirebaseCutsceneListener;
import com.shinav.mathapp.firebase.listener.FirebaseCutsceneNoticeListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionApproachListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionApproachPartListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionExplanationListener;
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

    @Inject FirebaseCutsceneListener firebaseCutsceneListener;
    @Inject FirebaseCutsceneLineListener firebaseCutsceneLineListener;
    @Inject FirebaseCutsceneNoticeListener firebaseCutsceneNoticeListener;

    @Inject FirebaseQuestionListener firebaseQuestionListener;
    @Inject FirebaseQuestionApproachListener firebaseQuestionApproachListener;
    @Inject FirebaseQuestionApproachPartListener firebaseQuestionApproachPartListener;
    @Inject FirebaseQuestionExplanationListener firebaseQuestionExplanationListener;

    @Inject
    public FirebaseChildRegisterer() { }

    public void register() {
        addChildEventListener(Nodes.TUTORIALS, firebaseTutorialListener);
        addChildEventListener(Nodes.TUTORIAL_FRAMES, firebaseTutorialFrameListener);

        addChildEventListener(Nodes.STORYBOARD, firebaseStoryboardListener);
        addChildEventListener(Nodes.STORYBOARD_FRAMES, firebaseStoryboardFrameListener);

        addChildEventListener(Nodes.CUTSCENES, firebaseCutsceneListener);
        addChildEventListener(Nodes.CUTSCENE_LINES, firebaseCutsceneLineListener);
        addChildEventListener(Nodes.CUTSCENE_NOTICES, firebaseCutsceneNoticeListener);

        addChildEventListener(Nodes.QUESTIONS, firebaseQuestionListener);
        addChildEventListener(Nodes.QUESTION_APPROACHES, firebaseQuestionApproachListener);
        addChildEventListener(Nodes.QUESTION_APPROACH_PARTS, firebaseQuestionApproachPartListener);
        addChildEventListener(Nodes.QUESTION_EXPLANATIONS, firebaseQuestionExplanationListener);
    }

    private void addChildEventListener(String node, ChildEventListener listener) {
        firebase.child(node).addChildEventListener(listener);
    }

}
