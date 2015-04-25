package com.shinav.mathapp.injection.component;

import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.main.MainActivity;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.questionApproach.QuestionApproachActivity;
import com.shinav.mathapp.questionApproach.QuestionApproachDragRecyclerView;
import com.shinav.mathapp.questionApproachFeedback.QAFActivity;
import com.shinav.mathapp.tutorial.TutorialActivity;
import com.shinav.mathapp.tutorial.TutorialConversationActivity;
import com.shinav.mathapp.tutorial.TutorialQAFActivity;
import com.shinav.mathapp.tutorial.TutorialQuestionActivity;
import com.shinav.mathapp.tutorial.TutorialQuestionApproachActivity;

import dagger.Component;

@ForActivity
@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                ActivityModule.class
        }
)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(TutorialActivity tutorialActivity);
    void inject(TutorialConversationActivity tutorialConversationActivity);
    void inject(TutorialQuestionActivity tutorialQuestionActivity);
    void inject(TutorialQuestionApproachActivity tutorialApproachActivity);
    void inject(TutorialQAFActivity tutorialApproachFeedbackActivity);

    void inject(ConversationActivity conversationActivity);

    void inject(QuestionApproachActivity questionApproachActivity);
    void inject(QuestionApproachDragRecyclerView questionApproachDragRecyclerView);
    void inject(QAFActivity QAFActivity);
    void inject(QuestionActivity questionActivity);
}
