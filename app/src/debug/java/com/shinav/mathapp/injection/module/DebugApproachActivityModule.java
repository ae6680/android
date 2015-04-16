package com.shinav.mathapp.injection.module;

import android.content.Context;

import com.shinav.mathapp.db.dataMapper.ApproachPartMapper;
import com.shinav.mathapp.db.dataMapper.QuestionApproachMapper;
import com.shinav.mathapp.db.dataMapper.QuestionMapper;
import com.shinav.mathapp.db.helper.DbOpenHelper;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.injection.annotation.ForApplication;
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

    @Provides QuestionMapper provideQuestionMapper() {
        if (mockMode) {
            return Mockito.mock(QuestionMapper.class);
        } else {
            return new QuestionMapper();
        }
    }

    @Provides QuestionApproachMapper provideApproachMapper() {
        if (mockMode) {
            return Mockito.mock(QuestionApproachMapper.class);
        } else {
            return new QuestionApproachMapper();
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
