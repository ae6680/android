package com.shinav.mathapp.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.shinav.mathapp.db.dataMapper.CutsceneDataMapper;
import com.shinav.mathapp.db.dataMapper.CutsceneLineDataMapper;
import com.shinav.mathapp.db.dataMapper.CutsceneNoticeDataMapper;
import com.shinav.mathapp.db.dataMapper.QuestionApproachDataMapper;
import com.shinav.mathapp.db.dataMapper.QuestionApproachPartDataMapper;
import com.shinav.mathapp.db.dataMapper.QuestionExplanationDataMapper;
import com.shinav.mathapp.db.dataMapper.StoryboardDataMapper;
import com.shinav.mathapp.db.dataMapper.StoryboardFrameDataMapper;
import com.shinav.mathapp.db.dataMapper.TutorialDataMapper;
import com.shinav.mathapp.db.dataMapper.TutorialFrameDataMapper;
import com.shinav.mathapp.firebase.mapper.CutsceneFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.CutsceneLineFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.CutsceneNoticeFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.QuestionApproachFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.QuestionApproachPartFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.QuestionExplanationFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.StoryboardFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.StoryboardFrameFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.TutorialFirebaseMapper;
import com.shinav.mathapp.firebase.mapper.TutorialFrameFirebaseMapper;

import javax.inject.Inject;

import static com.shinav.mathapp.firebase.FirebaseNodes.Nodes;

public class FirebaseChildRegisterer {

    private Firebase firebase;

    private FirebaseListener<TutorialFirebaseMapper, TutorialDataMapper> firebaseTutorialListener;
    private FirebaseListener<TutorialFrameFirebaseMapper, TutorialFrameDataMapper> firebaseTutorialFrameListener;

    private FirebaseListener<StoryboardFirebaseMapper, StoryboardDataMapper> firebaseStoryboardListener;
    private FirebaseListener<StoryboardFrameFirebaseMapper, StoryboardFrameDataMapper> firebaseStoryboardFrameListener;

    private FirebaseListener<CutsceneFirebaseMapper, CutsceneDataMapper> firebaseCutsceneListener;
    private FirebaseListener<CutsceneLineFirebaseMapper, CutsceneLineDataMapper> firebaseCutsceneLineListener;
    private FirebaseListener<CutsceneNoticeFirebaseMapper, CutsceneNoticeDataMapper> firebaseCutsceneNoticeListener;

    private FirebaseQuestionListener firebaseQuestionListener;
    private FirebaseListener<QuestionApproachFirebaseMapper, QuestionApproachDataMapper> firebaseQuestionApproachListener;
    private FirebaseListener<QuestionApproachPartFirebaseMapper, QuestionApproachPartDataMapper> firebaseQuestionApproachPartListener;
    private FirebaseListener<QuestionExplanationFirebaseMapper, QuestionExplanationDataMapper> firebaseQuestionExplanationListener;

    @Inject
    public FirebaseChildRegisterer(
            Firebase firebase,
            FirebaseListener<TutorialFirebaseMapper, TutorialDataMapper> firebaseTutorialListener,
            FirebaseListener<TutorialFrameFirebaseMapper, TutorialFrameDataMapper> firebaseTutorialFrameListener,
            FirebaseListener<StoryboardFirebaseMapper, StoryboardDataMapper> firebaseStoryboardListener,
            FirebaseListener<StoryboardFrameFirebaseMapper, StoryboardFrameDataMapper> firebaseStoryboardFrameListener,
            FirebaseListener<CutsceneFirebaseMapper, CutsceneDataMapper> firebaseCutsceneListener,
            FirebaseListener<CutsceneLineFirebaseMapper, CutsceneLineDataMapper> firebaseCutsceneLineListener,
            FirebaseListener<CutsceneNoticeFirebaseMapper, CutsceneNoticeDataMapper> firebaseCutsceneNoticeListener,
            FirebaseQuestionListener firebaseQuestionListener,
            FirebaseListener<QuestionApproachFirebaseMapper, QuestionApproachDataMapper> firebaseQuestionApproachListener,
            FirebaseListener<QuestionApproachPartFirebaseMapper, QuestionApproachPartDataMapper> firebaseQuestionApproachPartListener,
            FirebaseListener<QuestionExplanationFirebaseMapper, QuestionExplanationDataMapper> firebaseQuestionExplanationListener
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
