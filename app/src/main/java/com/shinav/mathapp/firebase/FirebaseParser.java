package com.shinav.mathapp.firebase;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.model.Approach;
import com.shinav.mathapp.db.model.ApproachPart;
import com.shinav.mathapp.db.model.Conversation;
import com.shinav.mathapp.db.model.ConversationPart;
import com.shinav.mathapp.db.model.Question;
import com.shinav.mathapp.db.model.Story;
import com.shinav.mathapp.db.model.StoryPart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

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

            question.setKey(dataSnapshot.getKey());
            question.setAnswer(answer);
            question.setValue(value);
            question.setTitle(title);

            Approach approach = parseApproach(question.getKey());

            DataSnapshot approachesSnapshot = dataSnapshot.child(FirebaseInterface.Question.APPROACHES);

            List<ApproachPart> approachParts = new ArrayList<>();
            for (int i = 0; i < approachesSnapshot.getChildrenCount(); i++) {
                ApproachPart approachPart = parseApproachPart(approachesSnapshot.child("approach-" + i));
                approachPart.setApproachKey(approach.getKey());
                approachParts.add(approachPart);
            }

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

    private Approach parseApproach(String questionKey) {
        Approach approach = new Approach();

        approach.setKey(UUID.randomUUID().toString());
        approach.setQuestionKey(questionKey);

        return approach;
    };

    private ApproachPart parseApproachPart(DataSnapshot dataSnapshot) {

        ApproachPart approachPart = new ApproachPart();

        String position =  getString(dataSnapshot, FirebaseInterface.Approach.POSITION);
        String value =     getString(dataSnapshot, FirebaseInterface.Approach.VALUE);

        approachPart.setPosition(Integer.parseInt(position));
        approachPart.setValue(value);

        return approachPart;
    }

    public Story parseStory(DataSnapshot dataSnapshot) {
        Story story = new Story();

        story.setKey(dataSnapshot.getKey());

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

            storyPart.setKey(dataSnapshot.getKey());

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

        conversation.setKey(dataSnapshot.getKey());

        return conversation;
    }

    private ConversationPart parseConversationPart(DataSnapshot dataSnapshot, String parentKey) {
        ConversationPart conversationPart = new ConversationPart();

        String position = getString(dataSnapshot, FirebaseInterface.ConversationPart.POSITION);
        String message = getString(dataSnapshot, FirebaseInterface.ConversationPart.MESSAGE);
        String delay = getString(dataSnapshot, FirebaseInterface.ConversationPart.DELAY);
        String typingDuration = getString(dataSnapshot, FirebaseInterface.ConversationPart.TYPING_DURATION);

        conversationPart.setPosition(Integer.parseInt(position));
        conversationPart.setMessage(message);
        conversationPart.setDelay(Integer.parseInt(delay));
        conversationPart.setTypingDuration(Integer.parseInt(typingDuration));

        conversationPart.setKey(parentKey + "-" + dataSnapshot.getKey());

        return conversationPart;
    }


}
