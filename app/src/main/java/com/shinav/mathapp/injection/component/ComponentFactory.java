package com.shinav.mathapp.injection.component;

import android.content.Context;

import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.injection.module.ApplicationModule;
import com.shinav.mathapp.injection.module.ViewModule;

public class ComponentFactory {

    private static ApplicationComponent component;

    public static ActivityComponent getActivityComponent(Context context) {
        return Dagger_ActivityComponent.builder()
                .applicationComponent(getApplicationComponent(context.getApplicationContext()))
                .activityModule(new ActivityModule(context))
                .build();
    }

    public static ViewComponent getViewComponent(Context context) {
        return Dagger_ViewComponent.builder()
                .applicationComponent(getApplicationComponent(context.getApplicationContext()))
                .viewModule(new ViewModule())
                .build();
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        if (component == null) {
            component = Dagger_ApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(context.getApplicationContext()))
                    .build();
        }
        return component;
    }

}
