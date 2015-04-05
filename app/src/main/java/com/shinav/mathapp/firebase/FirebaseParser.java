package com.shinav.mathapp.firebase;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.db.pojo.ConversationPart;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.Story;
import com.shinav.mathapp.db.pojo.StoryPart;

import javax.inject.Inject;

public class FirebaseParser {

    @Inject
    public FirebaseParser() { }

    private String getString(DataSnapshot dataSnapshot, String attribute) {
        return dataSnapshot.child(attribute).getValue().toString();
    }

    public Question parseQuestion(DataSnapshot dataSnapshot) {
        Question question = new Question();

        String answer = getString(dataSnapshot, Tables.Question.ANSWER);
        String value =  getString(dataSnapshot, Tables.Question.VALUE);
        String title =  getString(dataSnapshot, Tables.Question.TITLE);

        question.setKey(dataSnapshot.getKey());
        question.setAnswer(answer);
        question.setValue(value);
        question.setTitle(title);

        return question;
    }

    public Approach parseApproach(DataSnapshot dataSnapshot) {
        Approach approach = new Approach();

        String key = dataSnapshot.getKey();
        String questionKey = getString(dataSnapshot, Tables.Approach.QUESTION_KEY);

        approach.setKey(key);
        approach.setQuestionKey(questionKey);

        return approach;
    }

    public ApproachPart parseApproachPart(DataSnapshot dataSnapshot) {
        ApproachPart approachPart = new ApproachPart();

        String approachKey = getString(dataSnapshot, Tables.ApproachPart.APPROACH_KEY);
        String position =    getString(dataSnapshot, Tables.ApproachPart.POSITION);
        String value =       getString(dataSnapshot, Tables.ApproachPart.VALUE);

        approachPart.setKey(dataSnapshot.getKey());
        approachPart.setApproachKey(approachKey);
        approachPart.setPosition(Integer.parseInt(position));
        approachPart.setValue(value);

        return approachPart;
    }

    public Story parseStory(DataSnapshot dataSnapshot) {
        Story story = new Story();

        story.setKey(dataSnapshot.getKey());

        return story;
    }

    public StoryPart parseStoryPart(DataSnapshot dataSnapshot) {
        StoryPart storyPart = new StoryPart();

        String position = getString(dataSnapshot, Tables.StoryPart.POSITION);
        String type =     getString(dataSnapshot, Tables.StoryPart.TYPE);
        String typeKey =  getString(dataSnapshot, Tables.StoryPart.TYPE_KEY);
        String storyKey = getString(dataSnapshot, Tables.StoryPart.STORY_KEY);

        storyPart.setKey(dataSnapshot.getKey());
        storyPart.setStoryKey(storyKey);
        storyPart.setPosition(Integer.parseInt(position));
        storyPart.setType(type);
        storyPart.setTypeKey(typeKey);

        storyPart.setKey(dataSnapshot.getKey());

        return storyPart;
    }

    public Conversation parseConversation(DataSnapshot dataSnapshot) {
        Conversation conversation = new Conversation();

        String title = getString(dataSnapshot, Tables.Conversation.TITLE);

        conversation.setKey(dataSnapshot.getKey());
        conversation.setTitle(title);

        return conversation;
    }

    public ConversationPart parseConversationPart(DataSnapshot dataSnapshot) {
        ConversationPart conversationPart = new ConversationPart();

        String conversation_key = getString(dataSnapshot, Tables.ConversationPart.CONVERSATION_KEY);
        String message = getString(dataSnapshot, Tables.ConversationPart.MESSAGE);
        String position = getString(dataSnapshot, Tables.ConversationPart.POSITION);
        String delay = getString(dataSnapshot, Tables.ConversationPart.DELAY);
        String typingDuration = getString(dataSnapshot, Tables.ConversationPart.TYPING_DURATION);
        String alignment = getString(dataSnapshot, Tables.ConversationPart.ALIGNMENT);

        conversationPart.setKey(dataSnapshot.getKey());
        conversationPart.setConversationKey(conversation_key);
        conversationPart.setMessage(message);
        conversationPart.setPosition(Integer.parseInt(position));
        conversationPart.setDelay(Integer.parseInt(delay));
        conversationPart.setTypingDuration(Integer.parseInt(typingDuration));
        conversationPart.setAlignment(Integer.parseInt(alignment));

        return conversationPart;
    }

}
