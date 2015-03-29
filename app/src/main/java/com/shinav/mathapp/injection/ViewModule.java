package com.shinav.mathapp.injection;

import com.shinav.mathapp.approach.ApproachDragRecyclerView;
import com.shinav.mathapp.main.StoryCardRecyclerView;

import dagger.Module;

@Module(
        injects = {
                ApproachDragRecyclerView.class,
                StoryCardRecyclerView.class,
        },
        complete = false,
        library = true
)
public class ViewModule {

    public ViewModule() { }

}
