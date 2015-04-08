package com.shinav.mathapp.injection.component;

import com.shinav.mathapp.approach.ApproachDragRecyclerView;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.conversation.ConversationPartView;
import com.shinav.mathapp.injection.annotation.ForView;
import com.shinav.mathapp.injection.module.ViewModule;
import com.shinav.mathapp.main.practice.PracticeCardRecyclerView;
import com.shinav.mathapp.main.storyProgress.StoryCardRecyclerView;
import com.shinav.mathapp.main.storyProgress.StoryProgressPartAdapter;
import com.shinav.mathapp.question.card.QuestionCardView;
import com.shinav.mathapp.question.card.QuestionNextCardView;
import com.shinav.mathapp.tab.TabsView;
import com.shinav.mathapp.tutorial.TutorialView;

import dagger.Component;

@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                ViewModule.class
        }
)
@ForView
public interface ViewComponent {

    StoryProgressPartAdapter storyProgressPartAdapter();

    void inject(TabsView tabsView);
    void inject(ApproachDragRecyclerView approachDragRecyclerView);
    void inject(StoryCardRecyclerView storyCardRecyclerView);
    void inject(PracticeCardRecyclerView practiceCardRecyclerView);
    void inject(QuestionCardView questionCardView);
    void inject(QuestionNextCardView questionNextCardView);
    void inject(CalculatorFragment calculatorFragment);
    void inject(TutorialView tutorialView);
    void inject(ConversationPartView conversationPartView);
}
