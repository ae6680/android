package com.shinav.mathapp.sync;

import com.firebase.client.Firebase;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.firebase.listener.FirebaseApproachListener;
import com.shinav.mathapp.firebase.listener.FirebaseApproachPartListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationListener;
import com.shinav.mathapp.firebase.listener.FirebaseConversationPartListener;
import com.shinav.mathapp.firebase.listener.FirebaseQuestionListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryListener;
import com.shinav.mathapp.firebase.listener.FirebaseStoryPartListener;

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

        when(firebase.child(Nodes.STORIES)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.STORY_PARTS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.CONVERSATIONS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.CONVERSATION_PARTS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.QUESTIONS)).thenReturn(Mockito.mock(Firebase.class));
        when(firebase.child(Nodes.APPROACHES)).thenReturn(Mockito.mock(Firebase.class));

        new FirebaseChildRegisterer(
                firebase,
                Mockito.mock(FirebaseQuestionListener.class),
                Mockito.mock(FirebaseStoryListener.class),
                Mockito.mock(FirebaseConversationListener.class),
                Mockito.mock(FirebaseConversationPartListener.class),
                Mockito.mock(FirebaseApproachListener.class),
                Mockito.mock(FirebaseApproachPartListener.class),
                Mockito.mock(FirebaseStoryPartListener.class)
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
        Mockito.verify(firebase.child(Nodes.STORIES)).addChildEventListener(isA(FirebaseStoryListener.class));
    }

}
