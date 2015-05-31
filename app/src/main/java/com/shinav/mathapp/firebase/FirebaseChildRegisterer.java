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

    private Firebase firebase;

    private FirebaseTutorialListener firebaseTutorialListener;
    private FirebaseTutorialFrameListener firebaseTutorialFrameListener;

    private FirebaseStoryboardListener firebaseStoryboardListener;
    private FirebaseStoryboardFrameListener firebaseStoryboardFrameListener;

    private FirebaseCutsceneListener firebaseCutsceneListener;
    private FirebaseCutsceneLineListener firebaseCutsceneLineListener;
    private FirebaseCutsceneNoticeListener firebaseCutsceneNoticeListener;

    private FirebaseQuestionListener firebaseQuestionListener;
    private FirebaseQuestionApproachListener firebaseQuestionApproachListener;
    private FirebaseQuestionApproachPartListener firebaseQuestionApproachPartListener;
    private FirebaseQuestionExplanationListener firebaseQuestionExplanationListener;

    @Inject
    public FirebaseChildRegisterer(
            Firebase firebase,
            FirebaseTutorialListener firebaseTutorialListener,
            FirebaseTutorialFrameListener firebaseTutorialFrameListener,
            FirebaseStoryboardListener firebaseStoryboardListener,
            FirebaseStoryboardFrameListener firebaseStoryboardFrameListener,
            FirebaseCutsceneListener firebaseCutsceneListener,
            FirebaseCutsceneLineListener firebaseCutsceneLineListener,
            FirebaseCutsceneNoticeListener firebaseCutsceneNoticeListener,
            FirebaseQuestionListener firebaseQuestionListener,
            FirebaseQuestionApproachListener firebaseQuestionApproachListener,
            FirebaseQuestionApproachPartListener firebaseQuestionApproachPartListener,
            FirebaseQuestionExplanationListener firebaseQuestionExplanationListener
    ) {
        this.firebase = firebase;
        this.firebaseTutorialListener = firebaseTutorialListener;
        this.firebaseTutorialFrameListener = firebaseTutorialFrameListener;
        this.firebaseStoryboardListener = firebaseStoryboardListener;
        this.firebaseStoryboardFrameListener = firebaseStoryboardFrameListener;
        this.firebaseCutsceneListener = firebaseCutsceneListener;
        this.firebaseCutsceneLineListener = firebaseCutsceneLineListener;
        this.firebaseCutsceneNoticeListener = firebaseCutsceneNoticeListener;
        this.firebaseQuestionListener = firebaseQuestionListener;
        this.firebaseQuestionApproachListener = firebaseQuestionApproachListener;
        this.firebaseQuestionApproachPartListener = firebaseQuestionApproachPartListener;
        this.firebaseQuestionExplanationListener = firebaseQuestionExplanationListener;
    }

    public void register() {
        addChildEventListener(Nodes.TUTORIALS, firebaseTutorialListener);
        addChildEventListener(Nodes.TUTORIAL_FRAMES, firebaseTutorialFrameListener);

        addChildEventListener(Nodes.STORYBOARDS, firebaseStoryboardListener);
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
