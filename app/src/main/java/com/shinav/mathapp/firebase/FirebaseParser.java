package com.shinav.mathapp.firebase;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.approach.ApproachPart;
import com.shinav.mathapp.conversation.Conversation;
import com.shinav.mathapp.conversation.ConversationEntry;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.story.Story;
import com.shinav.mathapp.story.StoryEntry;

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

        RealmList<StoryEntry> storyEntries = new RealmList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            storyEntries.add(parseStoryEntry(snapshot));
        }

        story.setStoryEntries(storyEntries);

        story.setFirebaseKey(dataSnapshot.getKey());

        return story;
    }

    private StoryEntry parseStoryEntry(DataSnapshot dataSnapshot) {
        StoryEntry storyEntry = new StoryEntry();

        try {
            String position = getString(dataSnapshot, FirebaseInterface.StoryEntry.POSITION);
            String type =     getString(dataSnapshot, FirebaseInterface.StoryEntry.TYPE);
            String typeKey =  getString(dataSnapshot, FirebaseInterface.StoryEntry.TYPE_KEY);

            storyEntry.setPosition(Integer.parseInt(position));
            storyEntry.setType(type);
            storyEntry.setTypeKey(typeKey);

            storyEntry.setFirebaseKey(dataSnapshot.getKey());

        } catch (NullPointerException e) {
            Log.e(TAG, "Field or value not set");
            Log.e(TAG, "Key : " + dataSnapshot.getKey());
            Log.e(TAG, "Value : " + dataSnapshot.getValue());
            e.printStackTrace();

            return null;
        }

        return storyEntry;
    }

    public Conversation parseConversation(DataSnapshot dataSnapshot) {
        Conversation conversation = new Conversation();

        RealmList<ConversationEntry> conversationEntries = new RealmList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            conversationEntries.add(parseConversationEntry(snapshot, dataSnapshot.getKey()));
        }

        conversation.setConversationEntries(conversationEntries);

        conversation.setFirebaseKey(dataSnapshot.getKey());

        return conversation;
    }

    private ConversationEntry parseConversationEntry(DataSnapshot dataSnapshot, String parentKey) {
        ConversationEntry conversationEntry = new ConversationEntry();

        String position = getString(dataSnapshot, FirebaseInterface.ConversationEntry.POSITION);
        String message = getString(dataSnapshot, FirebaseInterface.ConversationEntry.MESSAGE);
        String delay = getString(dataSnapshot, FirebaseInterface.ConversationEntry.DELAY);
        String typingDuration = getString(dataSnapshot, FirebaseInterface.ConversationEntry.TYPING_DURATION);

        conversationEntry.setPosition(Integer.parseInt(position));
        conversationEntry.setMessage(message);
        conversationEntry.setDelay(Integer.parseInt(delay));
        conversationEntry.setTypingDuration(Integer.parseInt(typingDuration));

        conversationEntry.setFirebaseKey(parentKey + "-" + dataSnapshot.getKey());

        return conversationEntry;
    }


}
