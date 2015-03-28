package com.shinav.mathapp.injection;

import com.shinav.mathapp.approach.ApproachDragRecyclerView;

import dagger.Module;

@Module(
        injects = {
                ApproachDragRecyclerView.class,
        },
        complete = false,
        library = true
)
public class ViewModule {

    public ViewModule() { }

}
