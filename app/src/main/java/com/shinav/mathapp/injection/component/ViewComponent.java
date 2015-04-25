package com.shinav.mathapp.injection.component;

import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.conversation.ConversationLineView;
import com.shinav.mathapp.injection.annotation.ForView;
import com.shinav.mathapp.injection.module.ViewModule;
import com.shinav.mathapp.main.storyboard.StoryboardFrameAdapter;
import com.shinav.mathapp.main.storyboard.StoryboardFrameRecyclerView;
import com.shinav.mathapp.main.storyboard.viewHolder.StoryboardFrameViewHolder;
import com.shinav.mathapp.question.card.QuestionApproachCardView;
import com.shinav.mathapp.question.card.QuestionCardView;
import com.shinav.mathapp.question.card.QuestionNextCardView;
import com.shinav.mathapp.questionApproach.QuestionApproachDragRecyclerView;
import com.shinav.mathapp.questionApproachFeedback.QAFViewPager;
import com.shinav.mathapp.questionApproachFeedback.QAFViewPagerPage;
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

    StoryboardFrameAdapter storyProgressPartAdapter();

    void inject(TabsView tabsView);
    void inject(QuestionApproachDragRecyclerView questionApproachDragRecyclerView);
    void inject(StoryboardFrameRecyclerView storyboardFrameRecyclerView);
    void inject(QuestionCardView questionCardView);
    void inject(QuestionNextCardView questionNextCardView);
    void inject(CalculatorFragment calculatorFragment);
    void inject(TutorialView tutorialView);
    void inject(ConversationLineView conversationLineView);

    void inject(QAFViewPagerPage qafViewPagerPage);
    void inject(QAFViewPager qafViewPager);

    void inject(QuestionApproachCardView questionApproachCardView);

    void inject(StoryboardFrameViewHolder storyboardFrameViewHolder);
}
