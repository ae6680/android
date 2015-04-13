package com.shinav.mathapp.injection.component;

import android.content.Context;

import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.injection.TestDependencyWrapper;
import com.shinav.mathapp.injection.module.DebugApproachActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = DebugApproachActivityModule.class
)
@Singleton
public interface ApproachActivityComponent {

    void inject(ApproachActivity activity);
    void inject(TestDependencyWrapper wrapper);

    public final static class Initializer {
        public static ApproachActivityComponent init(Context context, boolean provideMocks) {

            DebugApproachActivityModule module =
                    new DebugApproachActivityModule(context, provideMocks);

            return DaggerApproachActivityComponent.builder()
                    .debugApproachActivityModule(module)
                    .build();

        }
    }

}
