package com.shinav.mathapp.sync;

import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.shinav.mathapp.firebase.FirebaseNodes.Nodes;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

public class FirebaseChildRegistererTest {

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

    @Before
    public void setUp() throws Exception {
        firebase = Mockito.mock(Firebase.class);

        firebaseTutorialListener = Mockito.mock(FirebaseTutorialListener.class);
        firebaseTutorialFrameListener = Mockito.mock(FirebaseTutorialFrameListener.class);

        firebaseStoryboardListener = Mockito.mock(FirebaseStoryboardListener.class);
        firebaseStoryboardFrameListener = Mockito.mock(FirebaseStoryboardFrameListener.class);

        firebaseCutsceneListener = Mockito.mock(FirebaseCutsceneListener.class);
        firebaseCutsceneLineListener = Mockito.mock(FirebaseCutsceneLineListener.class);
        firebaseCutsceneNoticeListener = Mockito.mock(FirebaseCutsceneNoticeListener.class);

        firebaseQuestionListener = Mockito.mock(FirebaseQuestionListener.class);
        firebaseQuestionApproachListener = Mockito.mock(FirebaseQuestionApproachListener.class);
        firebaseQuestionApproachPartListener = Mockito.mock(FirebaseQuestionApproachPartListener.class);
        firebaseQuestionExplanationListener = Mockito.mock(FirebaseQuestionExplanationListener.class);

        when(firebase.child(Nodes.TUTORIALS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.TUTORIAL_FRAMES)).thenReturn(Mockito.mock(Firebase.class));

        when(firebase.child(Nodes.STORYBOARDS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.STORYBOARD_FRAMES)).thenReturn(Mockito.mock(Firebase.class));

        when(firebase.child(Nodes.CUTSCENES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.CUTSCENE_LINES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.CUTSCENE_NOTICES)).thenReturn(Mockito.mock(Firebase.class));

        when(firebase.child(Nodes.QUESTIONS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.QUESTION_APPROACHES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.QUESTION_APPROACH_PARTS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.QUESTION_EXPLANATIONS)).thenReturn(Mockito.mock(Firebase.class));

        new FirebaseChildRegisterer(
                firebase,
                firebaseTutorialListener,
                firebaseTutorialFrameListener,
                firebaseStoryboardListener,
                firebaseStoryboardFrameListener,
                firebaseCutsceneListener,
                firebaseCutsceneLineListener,
                firebaseCutsceneNoticeListener,
                firebaseQuestionListener,
                firebaseQuestionApproachListener,
                firebaseQuestionApproachPartListener,
                firebaseQuestionExplanationListener
        ).register();
    }

    @Test
    public void testRegistersChildCutscenes() throws Exception {
        Mockito.verify(firebase.child(Nodes.CUTSCENES)).addChildEventListener(isA(FirebaseCutsceneListener.class));
    }

    @Test
    public void testRegistersChildTutorials() throws Exception {
        Mockito.verify(firebase.child(Nodes.TUTORIALS)).addChildEventListener(isA(FirebaseTutorialListener.class));
    }

    @Test
    public void testRegistersChildTutorialFrames() throws Exception {
        Mockito.verify(firebase.child(Nodes.TUTORIAL_FRAMES)).addChildEventListener(isA(FirebaseTutorialFrameListener.class));
    }

    @Test
    public void testRegistersChildStoryboards() throws Exception {
        Mockito.verify(firebase.child(Nodes.STORYBOARDS)).addChildEventListener(isA(FirebaseStoryboardListener.class));
    }

    @Test
    public void testRegistersChildStoryboardFrames() throws Exception {
        Mockito.verify(firebase.child(Nodes.STORYBOARD_FRAMES)).addChildEventListener(isA(FirebaseStoryboardFrameListener.class));
    }

    @Test
    public void testRegistersChildCutsceneLines() throws Exception {
        Mockito.verify(firebase.child(Nodes.CUTSCENE_LINES)).addChildEventListener(isA(FirebaseCutsceneLineListener.class));
    }

    @Test
    public void testRegistersChildCutsceneNotices() throws Exception {
        Mockito.verify(firebase.child(Nodes.CUTSCENE_NOTICES)).addChildEventListener(isA(FirebaseCutsceneNoticeListener.class));
    }

    @Test
    public void testRegistersChildQuestions() throws Exception {
        Mockito.verify(firebase.child(Nodes.QUESTIONS)).addChildEventListener(isA(FirebaseQuestionListener.class));
    }

    @Test
    public void testRegistersChildQuestionApproaches() throws Exception {
        Mockito.verify(firebase.child(Nodes.QUESTION_APPROACHES)).addChildEventListener(isA(FirebaseQuestionApproachListener.class));
    }

    @Test
    public void testRegistersChildQuestionApproachParts() throws Exception {
        Mockito.verify(firebase.child(Nodes.QUESTION_APPROACH_PARTS)).addChildEventListener(isA(FirebaseQuestionApproachPartListener.class));
    }

    @Test
    public void testRegistersChildQuestionExplanations() throws Exception {
        Mockito.verify(firebase.child(Nodes.QUESTION_EXPLANATIONS)).addChildEventListener(isA(FirebaseQuestionExplanationListener.class));
    }

}
