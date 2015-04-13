package com.shinav.mathapp.firebase;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.shinav.mathapp.db.pojo.TutorialFrame;

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
//        String explanation = getString(dataSnapshot, Tables.Question.EXPLANATION);
        String explanation = "Explanation not yet implemented.";

        question.setKey(dataSnapshot.getKey());
        question.setAnswer(answer);
        question.setValue(value);
        question.setTitle(title);
        question.setExplanation(explanation);

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

    public Storyboard parseStoryboard(DataSnapshot dataSnapshot) {
        Storyboard storyboard = new Storyboard();

        String perspective = getString(dataSnapshot, Tables.Storyboard.PERSPECTIVE);

        storyboard.setKey(dataSnapshot.getKey());
        storyboard.setPerspective(perspective);

        return storyboard;
    }

    public StoryboardFrame parseStoryboardFrame(DataSnapshot dataSnapshot) {
        StoryboardFrame storyboardFrame = new StoryboardFrame();

        String position = getString(dataSnapshot, Tables.StoryboardFrame.POSITION);
        String type =     getString(dataSnapshot, Tables.StoryboardFrame.FRAME_TYPE);
        String typeKey =  getString(dataSnapshot, Tables.StoryboardFrame.FRAME_TYPE_KEY);
        String storyKey = getString(dataSnapshot, Tables.StoryboardFrame.STORYBOARD_KEY);

        storyboardFrame.setKey(dataSnapshot.getKey());
        storyboardFrame.setStoryboardKey(storyKey);
        storyboardFrame.setPosition(Integer.parseInt(position));
        storyboardFrame.setFrameType(type);
        storyboardFrame.setFrameTypeKey(typeKey);

        return storyboardFrame;
    }

    public Conversation parseConversation(DataSnapshot dataSnapshot) {
        Conversation conversation = new Conversation();

        String title =      getString(dataSnapshot, Tables.Conversation.TITLE);
        String image_url =  getString(dataSnapshot, Tables.Conversation.BACKGROUND_IMAGE_URL);

        conversation.setKey(dataSnapshot.getKey());
        conversation.setTitle(title);
        conversation.setBackgroundImageUrl(image_url);

        return conversation;
    }

    public ConversationLine parseConversationLine(DataSnapshot dataSnapshot) {
        ConversationLine conversationLine = new ConversationLine();

        String conversation_key = getString(dataSnapshot, Tables.ConversationLine.CONVERSATION_KEY);
        String value =            getString(dataSnapshot, Tables.ConversationLine.VALUE);
        String position =         getString(dataSnapshot, Tables.ConversationLine.POSITION);
        String delay =            getString(dataSnapshot, Tables.ConversationLine.DELAY);
        String typingDuration =   getString(dataSnapshot, Tables.ConversationLine.TYPING_DURATION);
        String alignment =        getString(dataSnapshot, Tables.ConversationLine.ALIGNMENT);
        String image_url =        getString(dataSnapshot, Tables.ConversationLine.IMAGE_URL);

        conversationLine.setKey(dataSnapshot.getKey());
        conversationLine.setConversationKey(conversation_key);
        conversationLine.setValue(value);
        conversationLine.setPosition(Integer.parseInt(position));
        conversationLine.setDelay(Integer.parseInt(delay));
        conversationLine.setTypingDuration(Integer.parseInt(typingDuration));
        conversationLine.setAlignment(Integer.parseInt(alignment));
        conversationLine.setImageUrl(image_url);

        return conversationLine;
    }

    public Tutorial parseTutorial(DataSnapshot dataSnapshot) {
        Tutorial tutorial = new Tutorial();

        String perspective = getString(dataSnapshot, Tables.Tutorial.PERSPECTIVE);

        tutorial.setKey(dataSnapshot.getKey());
        tutorial.setPerspective(perspective);

        return tutorial;
    }

    public TutorialFrame parseTutorialFrame(DataSnapshot dataSnapshot) {
        TutorialFrame tutorialFrame = new TutorialFrame();

        String tutorialKey = getString(dataSnapshot, Tables.TutorialFrame.TUTORIAL_KEY);
        String position =    getString(dataSnapshot, Tables.TutorialFrame.POSITION);
        String frameType =        getString(dataSnapshot, Tables.TutorialFrame.FRAME_TYPE);
        String frameTypeKey =     getString(dataSnapshot, Tables.TutorialFrame.FRAME_TYPE_KEY);

        tutorialFrame.setKey(dataSnapshot.getKey());
        tutorialFrame.setTutorialKey(tutorialKey);
        tutorialFrame.setPosition(Integer.parseInt(position));
        tutorialFrame.setFrameType(frameType);
        tutorialFrame.setFrameTypeKey(frameTypeKey);

        return tutorialFrame;
    }

}
