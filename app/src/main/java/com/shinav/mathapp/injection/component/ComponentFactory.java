package com.shinav.mathapp.injection.component;

import android.content.Context;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.injection.module.ViewModule;

public class ComponentFactory {

    public static ActivityComponent getActivityComponent(Context context) {
        return Dagger_ActivityComponent.builder()
                .applicationComponent(MyApplication.getComponent())
                .activityModule(new ActivityModule(context))
                .build();
    }

    public static ViewComponent getViewComponent() {
        return Dagger_ViewComponent.builder()
                .applicationComponent(MyApplication.getComponent())
                .viewModule(new ViewModule())
                .build();
    }

}
