package com.shinav.mathapp.injection.module;

import android.content.Context;

import com.shinav.mathapp.db.helper.DbOpenHelper;
import com.shinav.mathapp.db.mapper.ApproachMapper;
import com.shinav.mathapp.db.mapper.ApproachPartMapper;
import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.injection.annotation.ForApplication;
import com.shinav.mathapp.progress.Storyteller;
import com.squareup.sqlbrite.SqlBrite;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugApproachActivityModule {

    private final boolean mockMode;
    private final Context context;

    public DebugApproachActivityModule(Context context, boolean provideMocks) {
        this.context = context;
        this.mockMode = provideMocks;
    }

    @Provides @ForActivity Context provideActivityContext() {
        return context;
    }

    @Provides @ForApplication Context provideApplicationContext() {
        return context.getApplicationContext();
    }

    @Provides Storyteller provideStoryteller() {
        if (mockMode) {
            return Mockito.mock(Storyteller.class);
        } else {
            return new Storyteller();
        }
    }

    @Provides QuestionMapper provideQuestionMapper() {
        if (mockMode) {
            return Mockito.mock(QuestionMapper.class);
        } else {
            return new QuestionMapper();
        }
    }

    @Provides ApproachMapper provideApproachMapper() {
        if (mockMode) {
            return Mockito.mock(ApproachMapper.class);
        } else {
            return new ApproachMapper();
        }
    }

    @Provides ApproachPartMapper provideApproachPartMapper() {
        if (mockMode) {
            return Mockito.mock(ApproachPartMapper.class);
        } else {
            return new ApproachPartMapper();
        }
    }

    @Provides @Singleton SqlBrite provideSqlBrite(DbOpenHelper helper) {
        return SqlBrite.create(helper);
    }

}
