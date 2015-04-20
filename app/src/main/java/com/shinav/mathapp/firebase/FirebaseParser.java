package com.shinav.mathapp.firebase;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.db.pojo.QuestionExplanation;
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
        String annexImageUrl = getString(dataSnapshot, Tables.Question.ANNEX_IMAGE_URL);
        String backgroundImageUrl = getString(dataSnapshot, Tables.Question.BACKGROUND_IMAGE_URL);

        question.setKey(dataSnapshot.getKey());
        question.setAnswer(answer);
        question.setValue(value);
        question.setTitle(title);
        question.setAnnexImageUrl(annexImageUrl);
        question.setBackgroundImageUrl(backgroundImageUrl);

        return question;
    }

    public QuestionApproach parseQuestionApproach(DataSnapshot dataSnapshot) {
        QuestionApproach questionApproach = new QuestionApproach();

        String questionKey = getString(dataSnapshot, Tables.QuestionApproach.QUESTION_KEY);

        questionApproach.setKey(dataSnapshot.getKey());
        questionApproach.setQuestionKey(questionKey);

        return questionApproach;
    }

    public QuestionApproachPart parseQuestionApproachPart(DataSnapshot dataSnapshot) {
        QuestionApproachPart questionApproachPart = new QuestionApproachPart();

        String approachKey = getString(dataSnapshot, Tables.QuestionApproachPart.QUESTION_APPROACH_KEY);
        String position =    getString(dataSnapshot, Tables.QuestionApproachPart.POSITION);
        String value =       getString(dataSnapshot, Tables.QuestionApproachPart.VALUE);

        questionApproachPart.setKey(dataSnapshot.getKey());
        questionApproachPart.setApproachKey(approachKey);
        questionApproachPart.setPosition(Integer.parseInt(position));
        questionApproachPart.setValue(value);

        return questionApproachPart;
    }

    public QuestionExplanation parseQuestionExplanation(DataSnapshot dataSnapshot) {
        QuestionExplanation questionExplanation = new QuestionExplanation();

        String questionKey = getString(dataSnapshot, Tables.QuestionExplanation.QUESTION_KEY);
        String text = getString(dataSnapshot, Tables.QuestionExplanation.TEXT);
        String imageUrl = getString(dataSnapshot, Tables.QuestionExplanation.IMAGE_URL);
        String position = getString(dataSnapshot, Tables.QuestionExplanation.POSITION);

        questionExplanation.setKey(dataSnapshot.getKey());
        questionExplanation.setQuestionKey(questionKey);
        questionExplanation.setText(text);
        questionExplanation.setImageUrl(imageUrl);
        questionExplanation.setPosition(Integer.parseInt(position));

        return questionExplanation;
    }

    public Storyboard parseStoryboard(DataSnapshot dataSnapshot) {
        Storyboard storyboard = new Storyboard();

        String title = getString(dataSnapshot, Tables.Storyboard.TITLE);

        storyboard.setKey(dataSnapshot.getKey());
        storyboard.setTitle(title);

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

        String title = getString(dataSnapshot, Tables.Tutorial.TITLE);

        tutorial.setKey(dataSnapshot.getKey());
        tutorial.setTitle(title);

        return tutorial;
    }

    public TutorialFrame parseTutorialFrame(DataSnapshot dataSnapshot) {
        TutorialFrame tutorialFrame = new TutorialFrame();

        String tutorialKey =    getString(dataSnapshot, Tables.TutorialFrame.TUTORIAL_KEY);
        String position =       getString(dataSnapshot, Tables.TutorialFrame.POSITION);
        String frameType =      getString(dataSnapshot, Tables.TutorialFrame.FRAME_TYPE);
        String frameTypeKey =   getString(dataSnapshot, Tables.TutorialFrame.FRAME_TYPE_KEY);

        tutorialFrame.setKey(dataSnapshot.getKey());
        tutorialFrame.setTutorialKey(tutorialKey);
        tutorialFrame.setPosition(Integer.parseInt(position));
        tutorialFrame.setFrameType(frameType);
        tutorialFrame.setFrameTypeKey(frameTypeKey);

        return tutorialFrame;
    }

}
