package com.shinav.mathapp.injection.module;

import com.shinav.mathapp.approach.ApproachDragRecyclerView;
import com.shinav.mathapp.main.practice.PracticeCardRecyclerView;
import com.shinav.mathapp.main.storyProgress.StoryCardRecyclerView;
import com.shinav.mathapp.question.card.QuestionCardView;
import com.shinav.mathapp.question.card.QuestionNextCardView;
import com.shinav.mathapp.tab.TabsView;

import dagger.Module;

@Module(
        injects = {
                TabsView.class,
                ApproachDragRecyclerView.class,
                StoryCardRecyclerView.class,
                PracticeCardRecyclerView.class,
                QuestionCardView.class,
                QuestionNextCardView.class,
        },
        complete = false,
        library = true,
        addsTo = AndroidModule.class
)
public class ViewModule {

    public ViewModule() { }

}
