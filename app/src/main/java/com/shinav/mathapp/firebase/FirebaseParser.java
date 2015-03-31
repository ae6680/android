package com.shinav.mathapp.firebase;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.approach.ApproachPart;
import com.shinav.mathapp.conversation.Conversation;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.story.Story;
import com.shinav.mathapp.story.StoryPart;

import javax.inject.Inject;

import io.realm.RealmList;

public class FirebaseParser {

    private static final String TAG = "FirebaseParser";

    @Inject
    public FirebaseParser() { }

    private String getString(DataSnapshot dataSnapshot, String attribute) {
        return dataSnapshot.child(attribute).getValue().toString();
    }

    public Question parseQuestion(DataSnapshot dataSnapshot) {
        Question question = new Question();

        try {
            String answer = getString(dataSnapshot, FirebaseInterface.Question.ANSWER);
            String value = getString(dataSnapshot, FirebaseInterface.Question.VALUE);
            String title = getString(dataSnapshot, FirebaseInterface.Question.TITLE);

            question.setFirebaseKey(dataSnapshot.getKey());
            question.setAnswer(answer);
            question.setValue(value);
            question.setTitle(title);

            DataSnapshot approachesSnapshot = dataSnapshot.child(FirebaseInterface.Question.APPROACHES);

            RealmList<ApproachPart> approachParts = new RealmList<>();
            for (int i = 0; i < approachesSnapshot.getChildrenCount(); i++) {
                approachParts.add(parseApproachPart(approachesSnapshot.child("approach-" + i)));
            }

            Approach approach = new Approach();
            approach.setFirebaseKey(question.getFirebaseKey() + "-approaches");
            approach.setApproachParts(approachParts);

            question.setApproach(approach);

            return question;

        } catch (NullPointerException e) {
            Log.e(TAG, "Field or value not set");
            Log.e(TAG, "Key : " + dataSnapshot.getKey());
            Log.e(TAG, "Value : " + dataSnapshot.getValue());
            e.printStackTrace();

            return null;
        }
    }

    private ApproachPart parseApproachPart(DataSnapshot dataSnapshot) {

        ApproachPart approachPart = new ApproachPart();

        String position =  getString(dataSnapshot, FirebaseInterface.Approach.POSITION);
        String value =     getString(dataSnapshot, FirebaseInterface.Approach.VALUE);

        approachPart.setPosition(Integer.parseInt(position));
        approachPart.setText(value);

        return approachPart;
    }

    public Story parseStory(DataSnapshot dataSnapshot) {
        Story story = new Story();

        RealmList<StoryPart> storyParts = new RealmList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            storyParts.add(parseStoryPart(snapshot));
        }

        story.setStoryParts(storyParts);

        story.setFirebaseKey(dataSnapshot.getKey());

        return story;
    }

    private StoryPart parseStoryPart(DataSnapshot dataSnapshot) {
        StoryPart storyPart = new StoryPart();

        try {
            String position = getString(dataSnapshot, FirebaseInterface.StoryPart.POSITION);
            String type =     getString(dataSnapshot, FirebaseInterface.StoryPart.TYPE);
            String typeKey =  getString(dataSnapshot, FirebaseInterface.StoryPart.TYPE_KEY);

            storyPart.setPosition(Integer.parseInt(position));
            storyPart.setType(type);
            storyPart.setTypeKey(typeKey);

            storyPart.setFirebaseKey(dataSnapshot.getKey());

        } catch (NullPointerException e) {
            Log.e(TAG, "Field or value not set");
            Log.e(TAG, "Key : " + dataSnapshot.getKey());
            Log.e(TAG, "Value : " + dataSnapshot.getValue());
            e.printStackTrace();

            return null;
        }

        return storyPart;
    }

    public Conversation parseConversation(DataSnapshot dataSnapshot) {
        Conversation conversation = new Conversation();

        RealmList<com.shinav.mathapp.conversation.ConversationPart> conversationParts = new RealmList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            conversationParts.add(parseConversationPart(snapshot, dataSnapshot.getKey()));
        }

        conversation.setConversationParts(conversationParts);

        conversation.setFirebaseKey(dataSnapshot.getKey());

        return conversation;
    }

    private com.shinav.mathapp.conversation.ConversationPart parseConversationPart(DataSnapshot dataSnapshot, String parentKey) {
        com.shinav.mathapp.conversation.ConversationPart conversationPart = new com.shinav.mathapp.conversation.ConversationPart();

        String position = getString(dataSnapshot, FirebaseInterface.ConversationPart.POSITION);
        String message = getString(dataSnapshot, FirebaseInterface.ConversationPart.MESSAGE);
        String delay = getString(dataSnapshot, FirebaseInterface.ConversationPart.DELAY);
        String typingDuration = getString(dataSnapshot, FirebaseInterface.ConversationPart.TYPING_DURATION);

        conversationPart.setPosition(Integer.parseInt(position));
        conversationPart.setMessage(message);
        conversationPart.setDelay(Integer.parseInt(delay));
        conversationPart.setTypingDuration(Integer.parseInt(typingDuration));

        conversationPart.setFirebaseKey(parentKey + "-" + dataSnapshot.getKey());

        return conversationPart;
    }


}
