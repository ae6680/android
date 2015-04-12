package com.shinav.mathapp.injection.component;

import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.approach.ApproachDragRecyclerView;
import com.shinav.mathapp.approach.feedback.ApproachFeedbackActivity;
import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.main.MainActivity;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.tutorial.TutorialApproachActivity;
import com.shinav.mathapp.tutorial.TutorialConversationActivity;
import com.shinav.mathapp.tutorial.TutorialQuestionActivity;

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

    void inject(TutorialConversationActivity tutorialConversationActivity);
    void inject(TutorialQuestionActivity tutorialQuestionActivity);
    void inject(TutorialApproachActivity tutorialApproachActivity);

    void inject(ConversationActivity conversationActivity);

    void inject(ApproachActivity approachActivity);
    void inject(ApproachDragRecyclerView approachDragRecyclerView);
    void inject(ApproachFeedbackActivity approachFeedbackActivity);

    void inject(QuestionActivity questionActivity);

}
