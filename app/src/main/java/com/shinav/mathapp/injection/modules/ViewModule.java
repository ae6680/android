package com.shinav.mathapp.injection.modules;

import com.shinav.mathapp.approach.ApproachDragRecyclerView;
import com.shinav.mathapp.main.practice.PracticeCardRecyclerView;
import com.shinav.mathapp.main.storyProgress.StoryCardRecyclerView;
import com.shinav.mathapp.view.TabsView;

import dagger.Module;

@Module(
        injects = {
                TabsView.class,
                ApproachDragRecyclerView.class,
                StoryCardRecyclerView.class,
                PracticeCardRecyclerView.class,
        },
        complete = false,
        library = true
)
public class ViewModule {

    public ViewModule() { }

}
