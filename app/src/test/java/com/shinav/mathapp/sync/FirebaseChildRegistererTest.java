package com.shinav.mathapp.sync;

import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.firebase.listener.FirebaseConversationLineListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionApproachListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionApproachPartListener;
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

    @Before
    public void setUp() throws Exception {
        firebase = Mockito.mock(Firebase.class);

        when(firebase.child(Nodes.STORYBOARD)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.STORYBOARD_FRAMES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.CONVERSATIONS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.CONVERSATION_LINES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.QUESTIONS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.APPROACHES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.APPROACH_PARTS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.TUTORIALS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.TUTORIAL_FRAMES)).thenReturn(Mockito.mock(Firebase.class));

        new FirebaseChildRegisterer(
                firebase,
                Mockito.mock(FirebaseQuestionListener.class),
                Mockito.mock(FirebaseStoryboardListener.class),
                Mockito.mock(FirebaseConversationListener.class),
                Mockito.mock(FirebaseConversationLineListener.class),
                Mockito.mock(FirebaseQuestionApproachListener.class),
                Mockito.mock(FirebaseQuestionApproachPartListener.class),
                Mockito.mock(FirebaseStoryboardFrameListener.class),
                Mockito.mock(FirebaseTutorialListener.class),
                Mockito.mock(FirebaseTutorialFrameListener.class)
        ).register();
    }

    @Test
    public void testRegistersChildQuestions() throws Exception {
        Mockito.verify(firebase.child(Nodes.QUESTIONS)).addChildEventListener(isA(FirebaseQuestionListener.class));
    }

    @Test
    public void testRegistersChildConversations() throws Exception {
        Mockito.verify(firebase.child(Nodes.CONVERSATIONS)).addChildEventListener(isA(FirebaseConversationListener.class));
    }

    @Test
    public void testRegistersChildStory() throws Exception {
        Mockito.verify(firebase.child(Nodes.STORYBOARD)).addChildEventListener(isA(FirebaseStoryboardListener.class));
    }

}
